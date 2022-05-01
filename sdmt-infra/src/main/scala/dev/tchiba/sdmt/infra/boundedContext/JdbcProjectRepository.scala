package dev.tchiba.sdmt.infra.boundedContext

import dev.tchiba.sdmt.core.models.boundedContext.{
  BoundedContext,
  BoundedContextAlias,
  BoundedContextId,
  BoundedContextName,
  BoundedContextOverview,
  ProjectRepository
}
import dev.tchiba.sdmt.infra.scalikejdbc.{DomainModels, Projects}
import scalikejdbc._

class JdbcProjectRepository extends ProjectRepository { // SQLInterporation trait をミックスインするとQueryDSLがうまく動かない模様

  import ProjectRepository._

  private val p = Projects.p

  override def findById(id: BoundedContextId): Option[BoundedContext] = DB readOnly { implicit session =>
    withSQL {
      select
        .from[Projects](Projects.as(p))
        .where
        .eq(p.projectId, id.string)
    }.map(Projects(p))
      .single()
      .apply()
      .map(reconstructFrom)
  }

  override def findByAlias(alias: BoundedContextAlias): Option[BoundedContext] = DB readOnly { implicit session =>
    withSQL {
      select
        .from(Projects.as(p))
        .where
        .eq(p.projectAlias, alias.value)
    }.map(Projects(p))
      .single()
      .apply()
      .map(reconstructFrom)
  }

  override def all: Seq[BoundedContext] = DB readOnly { implicit session =>
    Projects.findAll().map(reconstructFrom)
  }

  override def insert(project: BoundedContext): Unit = {
    DB localTx { implicit session =>
      withSQL {
        val column = Projects.column
        QueryDSL.insert
          .into(Projects)
          .namedValues(
            column.projectId       -> project.id.string,
            column.projectAlias    -> project.alias.value,
            column.projectName     -> project.name.value,
            column.projectOverview -> project.overview.value
          )
      }.update().apply()
    }
  }

  override def update(project: BoundedContext): Either[ConflictAlias, Unit] = {
    findByAlias(project.alias) match {
      case Some(sameAliasProject) if sameAliasProject != project => Left(ConflictAlias(sameAliasProject))
      case _ =>
        val updateResult = DB.localTx { implicit session =>
          withSQL {
            val column = Projects.column
            QueryDSL
              .update(Projects).set(
                column.projectId       -> project.id.string,
                column.projectAlias    -> project.alias.value,
                column.projectName     -> project.name.value,
                column.projectOverview -> project.overview.value
              )
              .where
              .eq(column.projectId, project.id.string)
          }.update().apply()
        }
        updateResult.ensuring(_ == 1, "updater target must only one record.")
        Right(())
    }
  }

  override def delete(id: BoundedContextId): Unit = {
    DB localTx { implicit session =>
      withSQL {
        QueryDSL.delete.from(Projects).where.eq(Projects.column.projectId, id.string)
      }.update().apply()
      withSQL {
        QueryDSL.delete.from(DomainModels).where.eq(DomainModels.column.projectId, id.string)
      }.update().apply()
    }
  }

  private def reconstructFrom(row: Projects): BoundedContext = {
    BoundedContext.reconstruct(
      id = BoundedContextId.fromString(row.projectId),
      alias = BoundedContextAlias(row.projectAlias),
      name = BoundedContextName(row.projectName),
      overview = BoundedContextOverview(row.projectOverview)
    )
  }
}

package dev.tchiba.sdmt.infra.boundedContext

import dev.tchiba.sdmt.core.models.boundedContext.{
  Project,
  ProjectAlias,
  ProjectId,
  ProjectName,
  ProjectOverview,
  ProjectRepository
}
import dev.tchiba.sdmt.infra.scalikejdbc.{DomainModels, Projects}
import scalikejdbc._

class JdbcProjectRepository extends ProjectRepository { // SQLInterporation trait をミックスインするとQueryDSLがうまく動かない模様

  import ProjectRepository._

  private val p = Projects.p

  override def findById(id: ProjectId): Option[Project] = DB readOnly { implicit session =>
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

  override def findByAlias(alias: ProjectAlias): Option[Project] = DB readOnly { implicit session =>
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

  override def all: Seq[Project] = DB readOnly { implicit session =>
    Projects.findAll().map(reconstructFrom)
  }

  override def insert(project: Project): Unit = {
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

  override def update(project: Project): Either[ConflictAlias, Unit] = {
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

  override def delete(id: ProjectId): Unit = {
    DB localTx { implicit session =>
      withSQL {
        QueryDSL.delete.from(Projects).where.eq(Projects.column.projectId, id.string)
      }.update().apply()
      withSQL {
        QueryDSL.delete.from(DomainModels).where.eq(DomainModels.column.projectId, id.string)
      }.update().apply()
    }
  }

  private def reconstructFrom(row: Projects): Project = {
    Project.reconstruct(
      id = ProjectId.fromString(row.projectId),
      alias = ProjectAlias(row.projectAlias),
      name = ProjectName(row.projectName),
      overview = ProjectOverview(row.projectOverview)
    )
  }
}

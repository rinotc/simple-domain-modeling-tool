package dev.tchiba.sdmt.infra.project

import dev.tchiba.sdmt.core.models.project.{
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

  override def update(project: Project): Unit = {
    DB localTx { implicit session =>
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
      }
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

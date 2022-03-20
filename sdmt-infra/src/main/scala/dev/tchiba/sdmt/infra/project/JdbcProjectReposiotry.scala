package dev.tchiba.sdmt.infra.project

import dev.tchiba.sdmt.core.models.project.{Project, ProjectAlias, ProjectId, ProjectRepository}
import scalikejdbc.{DB, SQLInterpolation, WrappedResultSet}

import java.util.UUID

class JdbcProjectRepository extends ProjectRepository with SQLInterpolation {

  override def findById(id: ProjectId): Option[Project] = DB readOnly { implicit session =>
    sql"""select * from main.public."project" where project_id = ${id.asString}"""
      .map(reconstructProjectFromResultSet)
      .single()
      .apply()
  }

  override def findByAlias(alias: ProjectAlias): Option[Project] = DB readOnly { implicit session =>
    sql"""select * from main.public."project" where project_alias = ${alias.value}"""
      .map(reconstructProjectFromResultSet)
      .single()
      .apply()
  }

  override def all: Seq[Project] = DB readOnly { implicit session =>
    sql"""select * from main.public."project""""
      .map(reconstructProjectFromResultSet)
      .list()
      .apply()
  }

  private def reconstructProjectFromResultSet(rs: WrappedResultSet): Project = Project.reconstruct(
    id = ProjectId(UUID.fromString(rs.string("project_id"))),
    alias = ProjectAlias(rs.string("project_alias")),
    name = rs.string("project_name"),
    overview = rs.string("project_overview")
  )

  override def insert(project: Project): Unit = {
    DB localTx { implicit session =>
      sql"""
           insert into main.public."project" (project_id, project_alias, project_name, project_overview) 
           values (${project.id.asString}, ${project.alias.value},${project.name}, ${project.overview})
         """
        .update()
        .apply()
    }
  }

  override def update(project: Project): Unit = {
    DB localTx { implicit session =>
      sql"""
            update main.public."project" 
            set project_alias = ${project.alias},
                project_name = ${project.name},
                project_overview = ${project.overview}
            where project_id = ${project.id.asString}
         """
        .update()
        .apply()
    }
  }

  override def delete(id: ProjectId): Unit = {
    DB localTx { implicit session =>
      sql"""delete from main.public."project" where project_id = ${id.asString}""".update().apply()
      sql"""delete from main.public."domain_model" where project_id = ${id.asString}""".update().apply()
    }
  }
}

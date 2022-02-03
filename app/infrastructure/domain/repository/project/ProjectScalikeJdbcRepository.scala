package infrastructure.domain.repository.project

import domain.models.project.{Project, ProjectId, ProjectRepository}
import scalikejdbc.{DB, SQLInterpolation}

import java.util.UUID

class ProjectScalikeJdbcRepository extends ProjectRepository with SQLInterpolation {

  override def findById(id: ProjectId): Option[Project] = DB readOnly { implicit session =>
    sql"""select * from main.public."project" where ${id.value}"""
      .map { rs =>
        Project.reconstruct(
          id = ProjectId(UUID.fromString(rs.string("project_id"))),
          name = rs.string("project_name"),
          overview = rs.string("project_overview")
        )
      }
      .single()
      .apply()
  }

  override def insert(project: Project): Unit = {
    DB localTx { implicit session =>
      sql"""
           insert into main.public."project" (project_id, project_name, project_overview) 
           values (${project.id.value}, ${project.name}, ${project.overview})
         """
        .update()
        .apply()
    }
  }

  override def update(project: Project): Unit = {
    DB localTx { implicit session =>
      sql"""
            update main.public."project" 
            set project_name = ${project.name},
                project_overview = ${project.overview}
            where project_id = ${project.id.value}
         """
        .update()
        .apply()
    }
  }

  override def delete(id: ProjectId): Unit = {
    DB localTx { implicit session =>
      sql"""
            delete from main.public."project" where project_id = ${id.value}
         """
        .update()
        .apply()
    }
  }
}

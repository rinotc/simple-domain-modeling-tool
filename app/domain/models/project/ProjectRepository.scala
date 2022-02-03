package domain.models.project

import support.Repository

trait ProjectRepository extends Repository[Project] {

  def findById(id: ProjectId): Option[Project]

  def insert(project: Project): Unit

  def update(project: Project): Unit

  def delete(id: ProjectId): Unit
}

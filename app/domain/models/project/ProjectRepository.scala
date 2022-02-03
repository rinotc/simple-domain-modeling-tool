package domain.models.project

import domain.Repository

trait ProjectRepository extends Repository[Project] {

  def findById(id: ProjectId): Option[Project]

  def all: Seq[Project]

  def insert(project: Project): Unit

  def update(project: Project): Unit

  def delete(id: ProjectId): Unit
}

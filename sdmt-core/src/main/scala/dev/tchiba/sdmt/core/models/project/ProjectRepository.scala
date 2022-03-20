package dev.tchiba.sdmt.core.models.project

import dev.tchiba.sdmt.core.Repository

trait ProjectRepository extends Repository[Project] {

  def findById(id: ProjectId): Option[Project]

  def findByAlias(alias: ProjectAlias): Option[Project]

  def all: Seq[Project]

  def insert(project: Project): Unit

  def update(project: Project): Unit

  def delete(id: ProjectId): Unit
}

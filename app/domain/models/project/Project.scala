package domain.models.project

import support.{Aggregate, Entity}

final class Project(
    val id: ProjectId,
    val projectName: String
) extends Entity[ProjectId]
    with Aggregate {

  def changeProjectName(newProjectName: String) = new Project(this.id, newProjectName)

  override def canEqual(that: Any): Boolean = that.isInstanceOf[Project]

  override def toString = s"Project(id=$id, projectName=$projectName)"
}

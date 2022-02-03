package domain.models.project

import domain.{Aggregate, Entity}

/**
 * プロジェクト
 *
 * @param id          プロジェクトID
 * @param name プロジェクト名称
 * @param overview    プロジェクト概要
 */
final class Project private (
    val id: ProjectId,
    val name: String,
    val overview: String
) extends Entity[ProjectId]
    with Aggregate {

  def changeProjectName(newProjectName: String): Project = copy(name = newProjectName)

  def changeOverview(newOverview: String): Project = copy(overview = newOverview)

  override def canEqual(that: Any): Boolean = that.isInstanceOf[Project]

  override def toString = s"Project(id=$id, projectName=$name)"

  private def copy(
      name: String = this.name,
      overview: String = this.overview
  ): Project = new Project(this.id, name, overview)
}

object Project {
  def reconstruct(id: ProjectId, name: String, overview: String) = new Project(id, name, overview)
}

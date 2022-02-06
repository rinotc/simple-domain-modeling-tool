package domain.models.project

import domain.{Aggregate, Entity}

import java.util.UUID

/**
 * プロジェクト
 *
 * @param id          プロジェクトID
 * @param name プロジェクト名称
 * @param overview    プロジェクト概要
 */
final class Project private (
    val id: ProjectId,
    val alias: ProjectAlias,
    val name: String,
    val overview: String
) extends Entity[ProjectId]
    with Aggregate {

  def changeProjectName(newProjectName: String): Project = copy(name = newProjectName)

  def changeOverview(newOverview: String): Project = copy(overview = newOverview)

  override def canEqual(that: Any): Boolean = that.isInstanceOf[Project]

  override def toString = s"Project(id=$id, projectName=$name)"

  private def copy(
      alias: ProjectAlias = this.alias,
      name: String = this.name,
      overview: String = this.overview
  ): Project = new Project(this.id, alias, name, overview)
}

object Project {
  def reconstruct(id: ProjectId, alias: ProjectAlias, name: String, overview: String) =
    new Project(id, alias, name, overview)

  def create(alias: ProjectAlias, name: String, overview: String) =
    new Project(ProjectId.generate, alias, name, overview)
}

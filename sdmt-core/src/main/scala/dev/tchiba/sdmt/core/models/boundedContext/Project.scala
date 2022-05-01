package dev.tchiba.sdmt.core.models.boundedContext

import dev.tchiba.sdmt.core.{Aggregate, Entity}

/**
 * プロジェクト
 *
 * @param id       プロジェクトID
 * @param name     プロジェクト名称
 * @param overview プロジェクト概要
 */
final class Project private (
    val id: ProjectId,
    val alias: ProjectAlias,
    val name: ProjectName,
    val overview: ProjectOverview
) extends Entity[ProjectId]
    with Aggregate {

  def changeAlias(newAlias: ProjectAlias): Project = copy(newAlias)

  def changeProjectName(newProjectName: ProjectName): Project = copy(name = newProjectName)

  def changeOverview(newOverview: ProjectOverview): Project = copy(overview = newOverview)

  override def canEqual(that: Any): Boolean = that.isInstanceOf[Project]

  private def copy(
      alias: ProjectAlias = this.alias,
      name: ProjectName = this.name,
      overview: ProjectOverview = this.overview
  ): Project = new Project(this.id, alias, name, overview)

  override def toString = s"Project(id=$id, alias=$alias, name=$name, overview=$overview)"
}

object Project {
  def reconstruct(id: ProjectId, alias: ProjectAlias, name: ProjectName, overview: ProjectOverview) =
    new Project(id, alias, name, overview)

  def create(alias: ProjectAlias, name: ProjectName, overview: ProjectOverview) =
    new Project(ProjectId.generate, alias, name, overview)
}

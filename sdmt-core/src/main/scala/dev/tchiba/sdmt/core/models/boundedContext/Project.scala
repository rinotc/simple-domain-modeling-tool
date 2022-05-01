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
    val id: BoundedContextId,
    val alias: BoundedContextAlias,
    val name: BoundedContextName,
    val overview: BoundedContextOverview
) extends Entity[BoundedContextId]
    with Aggregate {

  def changeAlias(newAlias: BoundedContextAlias): Project = copy(newAlias)

  def changeProjectName(newProjectName: BoundedContextName): Project = copy(name = newProjectName)

  def changeOverview(newOverview: BoundedContextOverview): Project = copy(overview = newOverview)

  override def canEqual(that: Any): Boolean = that.isInstanceOf[Project]

  private def copy(
      alias: BoundedContextAlias = this.alias,
      name: BoundedContextName = this.name,
      overview: BoundedContextOverview = this.overview
  ): Project = new Project(this.id, alias, name, overview)

  override def toString = s"Project(id=$id, alias=$alias, name=$name, overview=$overview)"
}

object Project {
  def reconstruct(
      id: BoundedContextId,
      alias: BoundedContextAlias,
      name: BoundedContextName,
      overview: BoundedContextOverview
  ) =
    new Project(id, alias, name, overview)

  def create(alias: BoundedContextAlias, name: BoundedContextName, overview: BoundedContextOverview) =
    new Project(BoundedContextId.generate, alias, name, overview)
}

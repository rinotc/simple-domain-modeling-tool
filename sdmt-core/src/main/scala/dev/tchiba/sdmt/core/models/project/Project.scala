package dev.tchiba.sdmt.core.models.project

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

  def changeProjectName(newProjectName: String): Either[String, Project] =
    ProjectName.validate(newProjectName).map { pn => copy(name = pn) }

  def changeOverview(newOverview: String): Either[String, Project] =
    ProjectOverview.validate(newOverview).map { o => copy(overview = o) }

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

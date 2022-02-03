package interfaces.viewmodels.project

import domain.models.project.Project

import java.util.UUID

/**
 * プロジェクト
 *
 * @param name     プロジェクト名称
 * @param overview プロジェクト概要
 */
case class ProjectViewModel(
    id: UUID,
    name: String,
    overview: String
)

object ProjectViewModel {
  def from(project: Project): ProjectViewModel = {
    ProjectViewModel(
      id = project.id.value,
      name = project.name,
      overview = project.overview
    )
  }
}

package support.project

import domain.models.project.ProjectId

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

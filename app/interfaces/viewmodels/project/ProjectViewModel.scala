package interfaces.viewmodels.project

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

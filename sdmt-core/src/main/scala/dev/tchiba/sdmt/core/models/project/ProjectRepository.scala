package dev.tchiba.sdmt.core.models.project

import dev.tchiba.sdmt.core.Repository

trait ProjectRepository extends Repository[Project] {

  import ProjectRepository.ConflictAlias

  def findById(id: ProjectId): Option[Project]

  def findByAlias(alias: ProjectAlias): Option[Project]

  def all: Seq[Project]

  def insert(project: Project): Unit

  /**
   * プロジェクトを更新する
   *
   * @param project プロジェクト
   * @return 更新成功時は何も返さない。
   *         エイリアスがコンフリクトした場合は [[ConflictAlias]] を返す。
   */
  def update(project: Project): Either[ConflictAlias, Unit]

  def delete(id: ProjectId): Unit
}

object ProjectRepository {

  /**
   * プロジェクトエイリアスがコンフリクトしている。
   *
   * @param conflictedProject エイリアスがコンフリクトしたプロジェクト
   */
  case class ConflictAlias(conflictedProject: Project)
}

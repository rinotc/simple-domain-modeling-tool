package dev.tchiba.sdmt.core.models.boundedContext

import dev.tchiba.sdmt.core.Repository

trait ProjectRepository extends Repository[BoundedContext] {

  import ProjectRepository.ConflictAlias

  def findById(id: BoundedContextId): Option[BoundedContext]

  def findByAlias(alias: BoundedContextAlias): Option[BoundedContext]

  def all: Seq[BoundedContext]

  def insert(project: BoundedContext): Unit

  /**
   * プロジェクトを更新する
   *
   * @param project プロジェクト
   * @return 更新成功時は何も返さない。
   *         エイリアスがコンフリクトした場合は [[ConflictAlias]] を返す。
   */
  def update(project: BoundedContext): Either[ConflictAlias, Unit]

  def delete(id: BoundedContextId): Unit
}

object ProjectRepository {

  /**
   * プロジェクトエイリアスがコンフリクトしている。
   *
   * @param conflictedProject エイリアスがコンフリクトしたプロジェクト
   */
  case class ConflictAlias(conflictedProject: BoundedContext)
}

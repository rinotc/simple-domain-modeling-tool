package dev.tchiba.sdmt.core.models.boundedContext

import dev.tchiba.sdmt.core.Repository

trait BoundedContextRepository extends Repository[BoundedContext] {

  import BoundedContextRepository.ConflictAlias

  def findById(id: BoundedContextId): Option[BoundedContext]

  def findByAlias(alias: BoundedContextAlias): Option[BoundedContext]

  def all: Seq[BoundedContext]

  def insert(boundedContext: BoundedContext): Unit

  /**
   * 境界づけられたコンテキストを更新する
   *
   * @param boundedContext 境界づけられたコンテキスト
   * @return 更新成功時は何も返さない。
   *         エイリアスがコンフリクトした場合は [[ConflictAlias]] を返す。
   */
  def update(boundedContext: BoundedContext): Either[ConflictAlias, Unit]

  def delete(id: BoundedContextId): Unit
}

object BoundedContextRepository {

  /**
   * 境界づけられたコンテキストのエイリアスがコンフリクトしている。
   *
   * @param conflictedProject エイリアスがコンフリクトした境界づけられたコンテキスト
   */
  case class ConflictAlias(conflictedProject: BoundedContext)
}

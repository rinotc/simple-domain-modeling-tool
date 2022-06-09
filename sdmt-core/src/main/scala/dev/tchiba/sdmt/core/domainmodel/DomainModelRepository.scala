package dev.tchiba.sdmt.core.domainmodel

import dev.tchiba.arch.ddd.Repository
import dev.tchiba.sdmt.core.boundedContext.BoundedContextId

trait DomainModelRepository extends Repository[DomainModel] {

  import DomainModelRepository._

  def findById(id: DomainModelId): Option[DomainModel]

  def findByEnglishName(englishName: EnglishName, boundedContextId: BoundedContextId): Option[DomainModel]

  def listBy(boundedContextId: BoundedContextId): Seq[DomainModel]

  def insert(domainMode: DomainModel): Either[ConflictEnglishName, Unit]

  def update(domainModel: DomainModel): Either[ConflictEnglishName, Unit]

  def delete(id: DomainModelId): Unit
}

object DomainModelRepository {

  /**
   * 境界づけられたコンテキスト内に自分自身を除く同じ英語名が存在する。
   *
   * @param conflictedModel コンフリクトしたモデル
   */
  case class ConflictEnglishName(conflictedModel: DomainModel)
}

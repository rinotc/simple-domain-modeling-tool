package dev.tchiba.sdmt.core.domainmodel

import dev.tchiba.sdmt.core.boundedContext.BoundedContextId
import dev.tchiba.sdmt.core.{Aggregate, Entity}

/**
 * ドメインモデル
 *
 * @param id               ドメインモデルID
 * @param boundedContextId 境界づけられたコンテキストID
 * @param ubiquitousName   ユビキタス名
 * @param englishName      英語名
 * @param knowledge        モデルの知識（Markdown）
 */
final class DomainModel private (
    val id: DomainModelId,
    val boundedContextId: BoundedContextId,
    val ubiquitousName: UbiquitousName,
    val englishName: EnglishName,
    val knowledge: Knowledge
) extends Entity[DomainModelId]
    with Aggregate {

  def changeUbiquitousName(name: UbiquitousName): DomainModel = copy(ubiquitousName = name)

  def changeEnglishName(name: EnglishName): DomainModel = copy(englishName = name)

  def changeKnowledge(knowledge: Knowledge): DomainModel = copy(knowledge = knowledge)

  override def canEqual(that: Any): Boolean = that.isInstanceOf[DomainModel]

  private def copy(
      ubiquitousName: UbiquitousName = this.ubiquitousName,
      englishName: EnglishName = this.englishName,
      knowledge: Knowledge = this.knowledge
  ): DomainModel = new DomainModel(this.id, this.boundedContextId, ubiquitousName, englishName, knowledge)

  override def toString = s"DomainModel($id, $boundedContextId, $ubiquitousName, $englishName, $knowledge)"
}

object DomainModel {
  def reconstruct(
      id: DomainModelId,
      boundedContextId: BoundedContextId,
      ubiquitousName: UbiquitousName,
      englishName: EnglishName,
      knowledge: Knowledge
  ): DomainModel = new DomainModel(id, boundedContextId, ubiquitousName, englishName, knowledge)

  def create(
      boundedContextId: BoundedContextId,
      ubiquitousName: UbiquitousName,
      englishName: EnglishName,
      knowledge: Knowledge
  ): DomainModel = {
    new DomainModel(
      id = DomainModelId.generate,
      boundedContextId = boundedContextId,
      ubiquitousName = ubiquitousName,
      englishName = englishName,
      knowledge = knowledge
    )
  }
}

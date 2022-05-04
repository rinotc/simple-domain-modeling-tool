package dev.tchiba.sdmt.core.domainmodel

import dev.tchiba.sdmt.core.boundedContext.BoundedContextId
import dev.tchiba.sdmt.core.{Aggregate, Entity}

/**
 * ドメインモデル
 *
 * @param id               ドメインモデルID
 * @param boundedContextId 境界づけられたコンテキストID
 * @param japaneseName     日本語名
 * @param englishName      英語名
 * @param specification    仕様（Markdown）
 */
final class DomainModel private (
    val id: DomainModelId,
    val boundedContextId: BoundedContextId,
    val japaneseName: JapaneseName,
    val englishName: EnglishName,
    val specification: Specification
) extends Entity[DomainModelId]
    with Aggregate {

  def changeJapaneseName(name: JapaneseName): DomainModel = copy(japaneseName = name)

  def changeEnglishName(name: EnglishName): DomainModel = copy(englishName = name)

  def changeSpecification(specification: Specification): DomainModel = copy(specification = specification)

  override def canEqual(that: Any): Boolean = that.isInstanceOf[DomainModel]

  private def copy(
      japaneseName: JapaneseName = this.japaneseName,
      englishName: EnglishName = this.englishName,
      specification: Specification = this.specification
  ): DomainModel = new DomainModel(this.id, this.boundedContextId, japaneseName, englishName, specification)

  override def toString = s"DomainModel($id, $boundedContextId, $japaneseName, $englishName, $specification)"
}

object DomainModel {
  def reconstruct(
      id: DomainModelId,
      boundedContextId: BoundedContextId,
      japaneseName: JapaneseName,
      englishName: EnglishName,
      specification: Specification
  ): DomainModel = new DomainModel(id, boundedContextId, japaneseName, englishName, specification)

  def create(
      boundedContextId: BoundedContextId,
      japaneseName: JapaneseName,
      englishName: EnglishName,
      specification: Specification
  ): DomainModel = {
    new DomainModel(
      id = DomainModelId.generate,
      boundedContextId = boundedContextId,
      japaneseName = japaneseName,
      englishName = englishName,
      specification = specification
    )
  }
}

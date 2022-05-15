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
 * @param specification    仕様（Markdown）
 */
final class DomainModel private (
    val id: DomainModelId,
    val boundedContextId: BoundedContextId,
    val ubiquitousName: UbiquitousName,
    val englishName: EnglishName,
    val specification: Specification
) extends Entity[DomainModelId]
    with Aggregate {

  def changeUbiquitousName(name: UbiquitousName): DomainModel = copy(ubiquitousName = name)

  def changeEnglishName(name: EnglishName): DomainModel = copy(englishName = name)

  def changeSpecification(specification: Specification): DomainModel = copy(specification = specification)

  override def canEqual(that: Any): Boolean = that.isInstanceOf[DomainModel]

  private def copy(
      ubiquitousName: UbiquitousName = this.ubiquitousName,
      englishName: EnglishName = this.englishName,
      specification: Specification = this.specification
  ): DomainModel = new DomainModel(this.id, this.boundedContextId, ubiquitousName, englishName, specification)

  override def toString = s"DomainModel($id, $boundedContextId, $ubiquitousName, $englishName, $specification)"
}

object DomainModel {
  def reconstruct(
      id: DomainModelId,
      boundedContextId: BoundedContextId,
      ubiquitousName: UbiquitousName,
      englishName: EnglishName,
      specification: Specification
  ): DomainModel = new DomainModel(id, boundedContextId, ubiquitousName, englishName, specification)

  def create(
      boundedContextId: BoundedContextId,
      ubiquitousName: UbiquitousName,
      englishName: EnglishName,
      specification: Specification
  ): DomainModel = {
    new DomainModel(
      id = DomainModelId.generate,
      boundedContextId = boundedContextId,
      ubiquitousName = ubiquitousName,
      englishName = englishName,
      specification = specification
    )
  }
}

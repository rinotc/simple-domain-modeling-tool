package dev.tchiba.sdmt.core.models.domainmodel

import dev.tchiba.sdmt.core.{Aggregate, Entity}
import dev.tchiba.sdmt.core.models.boundedContext.BoundedContextId
import laika.api.Transformer
import laika.format.{HTML, Markdown}
import laika.markdown.github.GitHubFlavor

/**
 * ドメインモデル
 *
 * @param id              ドメインモデルID
 * @param projectId       プロジェクトID
 * @param japaneseName    日本語名
 * @param englishName     英語名
 * @param specificationMD 仕様（Markdown）
 */
final class DomainModel private (
    val id: DomainModelId,
    val projectId: BoundedContextId,
    val japaneseName: String,
    val englishName: String,
    val specificationMD: String
) extends Entity[DomainModelId]
    with Aggregate {

  /**
   * Markdownで書かれた仕様をHTMLに変換する
   */
  def specificationToHtml: String = {
    val transformer = Transformer
      .from(Markdown)
      .to(HTML)
      .using(GitHubFlavor)
      .build

    transformer.transform(specificationMD).left.map(_.toString) match {
      case Left(value)  => value
      case Right(value) => value
    }
  }

  def changeJapaneseName(name: String): DomainModel = copy(japaneseName = name)

  def changeEnglishName(name: String): DomainModel = copy(englishName = name)

  def changeSpecification(specification: String): DomainModel = copy(specification = specification)

  override def canEqual(that: Any): Boolean = that.isInstanceOf[DomainModel]

  override def toString =
    s"DomainModel(id=$id, domainModelJapaneseName=$japaneseName, domainModelEnglishName=$englishName, specification=$specificationMD)"

  private def copy(
      japaneseName: String = this.japaneseName,
      englishName: String = this.englishName,
      specification: String = this.specificationMD
  ): DomainModel = new DomainModel(this.id, this.projectId, japaneseName, englishName, specification)
}

object DomainModel {
  def reconstruct(
      id: DomainModelId,
      projectId: BoundedContextId,
      japaneseName: String,
      englishName: String,
      specification: String
  ): DomainModel = new DomainModel(id, projectId, japaneseName, englishName, specification)

  def create(
      projectId: BoundedContextId,
      japaneseName: String,
      englishName: String,
      specification: String
  ): DomainModel = {
    new DomainModel(
      id = DomainModelId.generate,
      projectId = projectId,
      japaneseName = japaneseName,
      englishName = englishName,
      specificationMD = specification
    )
  }
}

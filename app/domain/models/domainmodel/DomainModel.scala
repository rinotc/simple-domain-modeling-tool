package domain.models.domainmodel

import domain.models.project.ProjectId
import play.twirl.api.Html
import support.{Aggregate, Entity}

final class DomainModel(
    val id: DomainModelId,
    val projectId: ProjectId,
    val japaneseName: String,
    val englishName: String,
    val specification: Html
) extends Entity[DomainModelId]
    with Aggregate {

  def changeJapaneseName(name: String): DomainModel = copy(japaneseName = name)

  def changeEnglishName(name: String): DomainModel = copy(englishName = name)

  def changeSpecification(specification: Html): DomainModel = copy(specification = specification)

  override def canEqual(that: Any): Boolean = that.isInstanceOf[DomainModel]

  override def toString =
    s"DomainModel(id=$id, domainModelJapaneseName=$japaneseName, domainModelEnglishName=$englishName, specification=$specification)"

  private def copy(
      japaneseName: String = this.japaneseName,
      englishName: String = this.englishName,
      specification: Html = this.specification
  ): DomainModel = new DomainModel(this.id, this.projectId, japaneseName, englishName, specification)
}

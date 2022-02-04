package domain.models.domainmodel

import domain.models.project.ProjectId
import domain.{Aggregate, Entity}
import play.twirl.api.Html

final class DomainModel private (
    val id: DomainModelId,
    val projectId: ProjectId,
    val japaneseName: String,
    val englishName: String,
    val specification: String
) extends Entity[DomainModelId]
    with Aggregate {

  def changeJapaneseName(name: String): DomainModel = copy(japaneseName = name)

  def changeEnglishName(name: String): DomainModel = copy(englishName = name)

  def changeSpecification(specification: String): DomainModel = copy(specification = specification)

  override def canEqual(that: Any): Boolean = that.isInstanceOf[DomainModel]

  override def toString =
    s"DomainModel(id=$id, domainModelJapaneseName=$japaneseName, domainModelEnglishName=$englishName, specification=$specification)"

  private def copy(
      japaneseName: String = this.japaneseName,
      englishName: String = this.englishName,
      specification: String = this.specification
  ): DomainModel = new DomainModel(this.id, this.projectId, japaneseName, englishName, specification)
}

object DomainModel {
  def reconstruct(
      id: DomainModelId,
      projectId: ProjectId,
      japaneseName: String,
      englishName: String,
      specification: String
  ): DomainModel = new DomainModel(id, projectId, japaneseName, englishName, specification)

  def create(projectId: ProjectId, japaneseName: String, englishName: String, specification: String): DomainModel = {
    new DomainModel(
      id = DomainModelId.generate,
      projectId = projectId,
      japaneseName = japaneseName,
      englishName = englishName,
      specification = specification
    )
  }
}

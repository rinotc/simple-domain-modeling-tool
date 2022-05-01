package dev.tchiba.sdmt.core.models.domainmodel

import dev.tchiba.sdmt.core.Repository
import dev.tchiba.sdmt.core.models.boundedContext.ProjectId

trait DomainModelRepository extends Repository[DomainModel] {

  def findById(id: DomainModelId): Option[DomainModel]

  def findByEnglishName(englishName: String, projectId: ProjectId): Option[DomainModel]

  def listBy(projectId: ProjectId): Seq[DomainModel]

  def insert(domainMode: DomainModel): Unit

  def update(domainModel: DomainModel): Unit

  def delete(id: DomainModelId): Unit
}

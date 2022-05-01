package dev.tchiba.sdmt.core.models.domainmodel

import dev.tchiba.sdmt.core.Repository
import dev.tchiba.sdmt.core.models.boundedContext.BoundedContextId

trait DomainModelRepository extends Repository[DomainModel] {

  def findById(id: DomainModelId): Option[DomainModel]

  def findByEnglishName(englishName: String, projectId: BoundedContextId): Option[DomainModel]

  def listBy(projectId: BoundedContextId): Seq[DomainModel]

  def insert(domainMode: DomainModel): Unit

  def update(domainModel: DomainModel): Unit

  def delete(id: DomainModelId): Unit
}

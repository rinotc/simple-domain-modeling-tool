package domain.models.domainmodel

import domain.Repository

trait DomainModelRepository extends Repository[DomainModel] {

  def findById(id: DomainModelId): Option[DomainModel]

  def insert(domainMode: DomainModel): Unit

  def update(domainModel: DomainModel): Unit

  def delete(id: DomainModelId): Unit
}

package domain.models.domainmodel

import domain.Repository

trait DomainModelRepository extends Repository[DomainModel] {

  def findById(id: DomainModelId): Option[DomainModel]

  def all: Seq[DomainModel]

  def insert(domainMode: DomainModel): Unit

  def update(domainModel: DomainModel): Unit

  def delete(id: DomainModelId): Unit
}

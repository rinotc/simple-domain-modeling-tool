package domain.models.domainmodel

import domain.Repository
import domain.models.project.ProjectId

trait DomainModelRepository extends Repository[DomainModel] {

  def findById(id: DomainModelId): Option[DomainModel]

  def listBy(projectId: ProjectId): Seq[DomainModel]

  def insert(domainMode: DomainModel): Unit

  def update(domainModel: DomainModel): Unit

  def delete(id: DomainModelId): Unit
}

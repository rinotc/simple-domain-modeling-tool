package dev.tchiba.sdmt.application.interactors.domainmodel.add

import dev.tchiba.sdmt.core.models.domainmodel.{DomainModel, DomainModelRepository}
import dev.tchiba.sdmt.core.models.boundedContext.BoundedContextRepository
import dev.tchiba.sdmt.usecase.domainmodel.add.{AddDomainModelInput, AddDomainModelOutput, AddDomainModelUseCase}

import javax.inject.Inject

class AddDomainModelInteractor @Inject() (
    projectRepository: BoundedContextRepository,
    domainModelRepository: DomainModelRepository
) extends AddDomainModelUseCase {

  override def handle(input: AddDomainModelInput): AddDomainModelOutput = {
    projectRepository.findByAlias(input.projectAlias) match {
      case None => AddDomainModelOutput.NoSuchProject(input.projectAlias)
      case Some(project) =>
        val newDomainModel = DomainModel.create(
          boundedCOntextId = project.id,
          japaneseName = input.japaneseName,
          englishName = input.englishName,
          specification = input.specification
        )
        domainModelRepository.findByEnglishName(newDomainModel.englishName, newDomainModel.boundedContextId) match {
          case Some(_) => AddDomainModelOutput.ConflictEnglishName(newDomainModel.englishName)
          case None =>
            domainModelRepository.insert(newDomainModel)
            AddDomainModelOutput.Success(newDomainModel)
        }
    }
  }
}

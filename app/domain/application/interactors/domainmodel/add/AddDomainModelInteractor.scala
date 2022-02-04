package domain.application.interactors.domainmodel.add

import domain.models.domainmodel.{DomainModel, DomainModelRepository}
import domain.models.project.ProjectRepository
import domain.usecases.domainmodel.add.{AddDomainModelInput, AddDomainModelOutput, AddDomainModelUseCase}

import javax.inject.Inject

class AddDomainModelInteractor @Inject() (
    projectRepository: ProjectRepository,
    domainModelRepository: DomainModelRepository
) extends AddDomainModelUseCase {

  override def handle(input: AddDomainModelInput): AddDomainModelOutput = {
    projectRepository.findByAlias(input.projectAlias) match {
      case None => AddDomainModelOutput.NoSuchProject(input.projectAlias)
      case Some(project) =>
        val newDomainModel = DomainModel.create(
          projectId = project.id,
          japaneseName = input.japaneseName,
          englishName = input.englishName,
          specification = input.specification
        )
        domainModelRepository.findByEnglishName(newDomainModel.englishName, newDomainModel.projectId) match {
          case Some(_) => AddDomainModelOutput.ConflictEnglishName(newDomainModel.englishName)
          case None =>
            domainModelRepository.insert(newDomainModel)
            AddDomainModelOutput.Success(newDomainModel)
        }
    }
  }
}

package domain.application.interactors.domainmodel.udpate

import domain.models.domainmodel.{DomainModelRepository, DomainModelValidator}
import domain.models.project.{ProjectId, ProjectRepository}
import domain.usecases.domainmodel.udpate.{UpdateDomainModelInput, UpdateDomainModelOutput, UpdateDomainModelUseCase}

import javax.inject.Inject

class UpdateDomainModelInteractor @Inject() (
    projectRepository: ProjectRepository,
    domainModelRepository: DomainModelRepository,
    domainModelValidator: DomainModelValidator
) extends UpdateDomainModelUseCase {
  override def handle(input: UpdateDomainModelInput): UpdateDomainModelOutput = {
    val maybeProjectAndModel = for {
      project <- projectRepository.findByAlias(input.projectAlias)
      model   <- domainModelRepository.findByEnglishName(input.englishNameNow, project.id)
    } yield (project, model)

    maybeProjectAndModel match {
      case None => UpdateDomainModelOutput.NotFoundSuchModel(input.projectAlias, input.englishNameNow)
      case Some((project, model)) =>
        if (domainModelValidator.isSameEnglishNameModelAlreadyExist(input.updatedEnglishName, project.id, model.id)) {
          UpdateDomainModelOutput.ConflictEnglishName(input.projectAlias, input.updatedEnglishName)
        } else {
          val updatedModel = model
            .changeJapaneseName(input.updatedJapaneseName)
            .changeEnglishName(input.updatedEnglishName)
            .changeSpecification(input.updatedSpecification)
          domainModelRepository.update(updatedModel)
          UpdateDomainModelOutput.Success(updatedModel, project)
        }
    }
  }
}

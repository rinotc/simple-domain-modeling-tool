package dev.tchiba.sdmt.application.interactors.domainmodel.update

import dev.tchiba.sdmt.core.models.domainmodel.{DomainModelRepository, DomainModelValidator}
import dev.tchiba.sdmt.core.models.boundedContext.BoundedContextRepository
import dev.tchiba.sdmt.usecase.domainmodel.update.{
  UpdateDomainModelInput,
  UpdateDomainModelOutput,
  UpdateDomainModelUseCase
}

import javax.inject.Inject

class UpdateDomainModelInteractor @Inject() (
    projectRepository: BoundedContextRepository,
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

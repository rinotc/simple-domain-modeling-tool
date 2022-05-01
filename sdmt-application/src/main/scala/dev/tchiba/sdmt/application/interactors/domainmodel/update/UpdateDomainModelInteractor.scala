package dev.tchiba.sdmt.application.interactors.domainmodel.update

import dev.tchiba.sdmt.core.boundedContext.BoundedContextRepository
import dev.tchiba.sdmt.core.domainmodel.{DomainModelRepository, DomainModelValidator}
import dev.tchiba.sdmt.usecase.domainmodel.update.{
  UpdateDomainModelInput,
  UpdateDomainModelOutput,
  UpdateDomainModelUseCase
}

import javax.inject.Inject

class UpdateDomainModelInteractor @Inject() (
    boundedContextRepository: BoundedContextRepository,
    domainModelRepository: DomainModelRepository,
    domainModelValidator: DomainModelValidator
) extends UpdateDomainModelUseCase {
  override def handle(input: UpdateDomainModelInput): UpdateDomainModelOutput = {
    val maybeContextAndModel = for {
      context <- boundedContextRepository.findByAlias(input.boundedContextAlias)
      model   <- domainModelRepository.findByEnglishName(input.englishNameNow, context.id)
    } yield (context, model)

    maybeContextAndModel match {
      case None => UpdateDomainModelOutput.NotFoundSuchModel(input.boundedContextAlias, input.englishNameNow)
      case Some((context, model)) =>
        if (domainModelValidator.isSameEnglishNameModelAlreadyExist(input.updatedEnglishName, context.id, model.id)) {
          UpdateDomainModelOutput.ConflictEnglishName(input.boundedContextAlias, input.updatedEnglishName)
        } else {
          val updatedModel = model
            .changeJapaneseName(input.updatedJapaneseName)
            .changeEnglishName(input.updatedEnglishName)
            .changeSpecification(input.updatedSpecification)
          domainModelRepository.update(updatedModel)
          UpdateDomainModelOutput.Success(updatedModel, context)
        }
    }
  }
}

package dev.tchiba.sdmt.application.interactors.domainmodel.update

import dev.tchiba.sdmt.core.boundedContext.BoundedContextRepository
import dev.tchiba.sdmt.core.domainmodel.DomainModelRepository
import dev.tchiba.sdmt.usecase.domainmodel.update.{
  UpdateDomainModelInput,
  UpdateDomainModelOutput,
  UpdateDomainModelUseCase
}

import javax.inject.Inject

class UpdateDomainModelInteractor @Inject() (
    boundedContextRepository: BoundedContextRepository,
    domainModelRepository: DomainModelRepository
) extends UpdateDomainModelUseCase {
  override def handle(input: UpdateDomainModelInput): UpdateDomainModelOutput = {

    val result = for {
      context <- boundedContextRepository
        .findById(input.boundedContextId).toRight {
          UpdateDomainModelOutput.NotFoundBoundedContext(input.boundedContextId)
        }
      model <- domainModelRepository.findById(input.domainModelId).toRight {
        UpdateDomainModelOutput.NotFoundSuchModel(context, input.domainModelId)
      }
      updatedModel = model
        .changeJapaneseName(input.updatedJapaneseName)
        .changeEnglishName(input.updatedEnglishName)
        .changeSpecification(input.updatedSpecification)
      _ <- domainModelRepository.update(updatedModel).left.map { conflict =>
        UpdateDomainModelOutput.ConflictEnglishName(context, conflict.conflictedModel)
      }
    } yield UpdateDomainModelOutput.Success(updatedModel, context)

    result.unwrap
  }
}

package dev.tchiba.sdmt.application.interactors.boundedContext.update

import dev.tchiba.sdmt.core.boundedContext.BoundedContextRepository
import dev.tchiba.sdmt.usecase.boundedContext.update.{
  UpdateBoundedContextInput,
  UpdateBoundedContextOutput,
  UpdateBoundedContextUseCase
}

import javax.inject.Inject

final class UpdateBoundedContextInteractor @Inject() (
    boundedContextRepository: BoundedContextRepository
) extends UpdateBoundedContextUseCase {
  override def handle(input: UpdateBoundedContextInput): UpdateBoundedContextOutput = {
    val result = for {
      boundedContext <- boundedContextRepository.findById(input.targetId).toRight {
        UpdateBoundedContextOutput.NotFound(input.targetId)
      }
      updatedBoundedContext = boundedContext
        .changeAlias(input.alias)
        .changeName(input.name)
        .changeOverview(input.overview)
      _ <- boundedContextRepository.update(updatedBoundedContext).left.map { conflict =>
        UpdateBoundedContextOutput.ConflictAlias(conflict.conflictedContext)
      }
    } yield UpdateBoundedContextOutput.Success(updatedBoundedContext)

    result.unwrap
  }
}

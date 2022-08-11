package dev.tchiba.sdmt.application.interactors.boundedContext.delete

import dev.tchiba.sdmt.core.boundedContext.BoundedContextRepository
import dev.tchiba.sdmt.usecase.boundedContext.delete.{
  DeleteBoundedContextInput,
  DeleteBoundedContextOutput,
  DeleteBoundedContextUseCase
}

import javax.inject.Inject

final class DeleteBoundedContextInteractor @Inject() (
    boundedContextRepository: BoundedContextRepository
) extends DeleteBoundedContextUseCase {
  override def handle(input: DeleteBoundedContextInput): DeleteBoundedContextOutput = {
    boundedContextRepository.delete(input.boundedContextId)
    DeleteBoundedContextOutput.Success
  }
}

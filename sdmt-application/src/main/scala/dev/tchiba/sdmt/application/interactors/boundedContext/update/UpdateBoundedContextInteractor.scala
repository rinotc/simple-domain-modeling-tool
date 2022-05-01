package dev.tchiba.sdmt.application.interactors.boundedContext.update

import dev.tchiba.sdmt.core.models.boundedContext.BoundedContextRepository
import dev.tchiba.sdmt.usecase.boundedContext.update.{
  UpdateBoundedContextInput,
  UpdateBoundedContextOutput,
  UpdateBoundedContextUseCase
}

import javax.inject.Inject

final class UpdateBoundedContextInteractor @Inject() (
    projectRepository: BoundedContextRepository
) extends UpdateBoundedContextUseCase {
  override def handle(input: UpdateBoundedContextInput): UpdateBoundedContextOutput = {
    val result = for {
      project <- projectRepository.findById(input.targetId).toRight {
        UpdateBoundedContextOutput.NotFound(input.targetId)
      }
      updatedProject = project
        .changeAlias(input.alias)
        .changeName(input.name)
        .changeOverview(input.overview)
      _ <- projectRepository.update(updatedProject).left.map { conflict =>
        UpdateBoundedContextOutput.ConflictAlias(conflict.conflictedContext)
      }
    } yield UpdateBoundedContextOutput.Success(updatedProject)

    result.unwrap
  }
}

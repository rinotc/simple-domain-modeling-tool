package dev.tchiba.sdmt.application.interactors.boundedContext.update

import dev.tchiba.sdmt.core.models.boundedContext.BoundedContextRepository
import dev.tchiba.sdmt.usecase.boundedContext.update.{UpdateProjectInput, UpdateProjectOutput, UpdateProjectUseCase}

import javax.inject.Inject

final class UpdateProjectInteractor @Inject() (
    projectRepository: BoundedContextRepository
) extends UpdateProjectUseCase {
  override def handle(input: UpdateProjectInput): UpdateProjectOutput = {
    val result = for {
      project <- projectRepository.findById(input.targetId).toRight { UpdateProjectOutput.NotFound(input.targetId) }
      updatedProject = project
        .changeAlias(input.alias)
        .changeProjectName(input.name)
        .changeOverview(input.overview)
      _ <- projectRepository.update(updatedProject).left.map { conflict =>
        UpdateProjectOutput.ConflictAlias(conflict.conflictedProject)
      }
    } yield UpdateProjectOutput.Success(updatedProject)

    result.unwrap
  }
}

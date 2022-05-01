package dev.tchiba.sdmt.application.interactors.boundedContext.create

import dev.tchiba.sdmt.core.models.boundedContext.{BoundedContext, BoundedContextRepository}
import dev.tchiba.sdmt.usecase.boundedContext.create.{CreateProjectInput, CreateProjectOutput, CreateProjectUseCase}

import javax.inject.Inject

class CreateProjectInteractor @Inject() (
    projectRepository: BoundedContextRepository
) extends CreateProjectUseCase {
  override def handle(input: CreateProjectInput): CreateProjectOutput = {
    projectRepository.findByAlias(input.projectAlias) match {
      case Some(_) => CreateProjectOutput.ConflictAlias(input.projectAlias)
      case None =>
        val newProject = BoundedContext.create(input.projectAlias, input.projectName, input.projectOverview)
        projectRepository.insert(newProject)
        CreateProjectOutput.Success(newProject)
    }
  }
}

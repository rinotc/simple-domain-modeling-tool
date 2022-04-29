package dev.tchiba.sdmt.application.interactors.project.create

import dev.tchiba.sdmt.core.models.project.{Project, ProjectRepository}
import dev.tchiba.sdmt.usecase.project.create.{CreateProjectInput, CreateProjectOutput, CreateProjectUseCase}

import javax.inject.Inject

class CreateProjectInteractor @Inject() (
    projectRepository: ProjectRepository
) extends CreateProjectUseCase {
  override def handle(input: CreateProjectInput): CreateProjectOutput = {
    projectRepository.findByAlias(input.projectAlias) match {
      case Some(_) => CreateProjectOutput.ConflictAlias(input.projectAlias)
      case None =>
        val newProject = Project.create(input.projectAlias, input.projectName, input.projectOverview)
        projectRepository.insert(newProject)
        CreateProjectOutput.Success(newProject)
    }
  }
}
package domain.application.interactors.project.add

import domain.models.project.{Project, ProjectRepository}
import domain.usecases.project.add.{AddProjectInput, AddProjectOutput, AddProjectUseCase}

import javax.inject.Inject

class AddProjectInteractor @Inject() (
    projectRepository: ProjectRepository
) extends AddProjectUseCase {
  override def handle(input: AddProjectInput): AddProjectOutput = {
    projectRepository.findByAlias(input.projectAlias) match {
      case Some(_) => AddProjectOutput.ConflictAlias(input.projectAlias)
      case None =>
        val newProject = Project.create(input.projectAlias, input.projectName, input.projectOverview)
        projectRepository.insert(newProject)
        AddProjectOutput.Success(newProject)
    }
  }
}

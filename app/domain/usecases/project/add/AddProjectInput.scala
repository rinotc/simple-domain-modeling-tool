package domain.usecases.project.add

import domain.models.project.ProjectAlias
import domain.usecases.Input

case class AddProjectInput(
    projectAlias: ProjectAlias,
    projectName: String,
    projectOverview: String
) extends Input[AddProjectOutput]

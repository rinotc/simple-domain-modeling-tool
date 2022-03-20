package dev.tchiba.sdmt.usecase.project.add

import dev.tchiba.sdmt.core.models.project.ProjectAlias
import dev.tchiba.sdmt.usecase.Input

case class AddProjectInput(
    projectAlias: ProjectAlias,
    projectName: String,
    projectOverview: String
) extends Input[AddProjectOutput]

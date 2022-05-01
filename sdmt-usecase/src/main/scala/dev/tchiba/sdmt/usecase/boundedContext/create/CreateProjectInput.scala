package dev.tchiba.sdmt.usecase.boundedContext.create

import dev.tchiba.sdmt.core.models.boundedContext.{ProjectAlias, ProjectName, ProjectOverview}
import dev.tchiba.sdmt.usecase.Input

case class CreateProjectInput(
    projectAlias: ProjectAlias,
    projectName: ProjectName,
    projectOverview: ProjectOverview
) extends Input[CreateProjectOutput]

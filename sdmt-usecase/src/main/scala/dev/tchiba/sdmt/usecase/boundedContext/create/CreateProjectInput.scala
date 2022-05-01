package dev.tchiba.sdmt.usecase.boundedContext.create

import dev.tchiba.sdmt.core.models.boundedContext.{BoundedContextAlias, ProjectName, ProjectOverview}
import dev.tchiba.sdmt.usecase.Input

case class CreateProjectInput(
    projectAlias: BoundedContextAlias,
    projectName: ProjectName,
    projectOverview: ProjectOverview
) extends Input[CreateProjectOutput]

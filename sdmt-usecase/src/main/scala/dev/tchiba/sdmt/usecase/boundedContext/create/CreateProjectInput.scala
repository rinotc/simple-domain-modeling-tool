package dev.tchiba.sdmt.usecase.boundedContext.create

import dev.tchiba.sdmt.core.models.boundedContext.{BoundedContextAlias, BoundedContextName, BoundedContextOverview}
import dev.tchiba.sdmt.usecase.Input

case class CreateProjectInput(
    projectAlias: BoundedContextAlias,
    projectName: BoundedContextName,
    projectOverview: BoundedContextOverview
) extends Input[CreateProjectOutput]

package dev.tchiba.sdmt.usecase.boundedContext.update

import dev.tchiba.sdmt.core.models.boundedContext.{
  BoundedContextAlias,
  BoundedContextId,
  BoundedContextName,
  ProjectOverview
}
import dev.tchiba.sdmt.usecase.Input

case class UpdateProjectInput(
    targetId: BoundedContextId,
    alias: BoundedContextAlias,
    name: BoundedContextName,
    overview: ProjectOverview
) extends Input[UpdateProjectOutput]

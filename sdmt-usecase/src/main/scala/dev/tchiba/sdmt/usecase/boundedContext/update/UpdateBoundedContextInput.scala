package dev.tchiba.sdmt.usecase.boundedContext.update

import dev.tchiba.sdmt.core.boundedContext.{
  BoundedContextAlias,
  BoundedContextId,
  BoundedContextName,
  BoundedContextOverview
}
import dev.tchiba.sdmt.usecase.Input

case class UpdateBoundedContextInput(
    targetId: BoundedContextId,
    alias: BoundedContextAlias,
    name: BoundedContextName,
    overview: BoundedContextOverview
) extends Input[UpdateBoundedContextOutput]

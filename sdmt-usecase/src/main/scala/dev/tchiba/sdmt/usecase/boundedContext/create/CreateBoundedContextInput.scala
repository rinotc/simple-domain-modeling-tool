package dev.tchiba.sdmt.usecase.boundedContext.create

import dev.tchiba.sdmt.core.models.boundedContext.{BoundedContextAlias, BoundedContextName, BoundedContextOverview}
import dev.tchiba.sdmt.usecase.Input

case class CreateBoundedContextInput(
    alias: BoundedContextAlias,
    name: BoundedContextName,
    overview: BoundedContextOverview
) extends Input[CreateBoundedContextOutput]

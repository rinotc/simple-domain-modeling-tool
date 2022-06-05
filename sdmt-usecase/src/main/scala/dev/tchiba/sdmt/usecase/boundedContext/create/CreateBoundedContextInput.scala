package dev.tchiba.sdmt.usecase.boundedContext.create

import dev.tchiba.arch.usecase.Input
import dev.tchiba.sdmt.core.boundedContext.{BoundedContextAlias, BoundedContextName, BoundedContextOverview}

case class CreateBoundedContextInput(
    alias: BoundedContextAlias,
    name: BoundedContextName,
    overview: BoundedContextOverview
) extends Input[CreateBoundedContextOutput]

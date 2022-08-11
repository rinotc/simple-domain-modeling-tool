package dev.tchiba.sdmt.usecase.boundedContext.delete

import dev.tchiba.arch.usecase.Input
import dev.tchiba.sdmt.core.boundedContext.BoundedContextId

final case class DeleteBoundedContextInput(boundedContextId: BoundedContextId) extends Input[DeleteBoundedContextOutput]

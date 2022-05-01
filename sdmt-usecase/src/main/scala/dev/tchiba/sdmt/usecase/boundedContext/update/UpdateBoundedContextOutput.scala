package dev.tchiba.sdmt.usecase.boundedContext.update

import dev.tchiba.sdmt.core.models.boundedContext.{BoundedContext, BoundedContextId}
import dev.tchiba.sdmt.usecase.Output

sealed abstract class UpdateBoundedContextOutput extends Output

object UpdateBoundedContextOutput {

  case class Success(updatedProject: BoundedContext) extends UpdateBoundedContextOutput

  case class NotFound(targetId: BoundedContextId) extends UpdateBoundedContextOutput

  case class ConflictAlias(conflictedProject: BoundedContext) extends UpdateBoundedContextOutput
}

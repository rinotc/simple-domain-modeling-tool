package dev.tchiba.sdmt.usecase.boundedContext.update

import dev.tchiba.sdmt.core.boundedContext.{BoundedContext, BoundedContextId}
import dev.tchiba.sdmt.usecase.Output

sealed abstract class UpdateBoundedContextOutput extends Output

object UpdateBoundedContextOutput {

  case class Success(updatedBoundedContext: BoundedContext) extends UpdateBoundedContextOutput

  case class NotFound(targetId: BoundedContextId) extends UpdateBoundedContextOutput

  case class ConflictAlias(conflictedBoundedContext: BoundedContext) extends UpdateBoundedContextOutput
}

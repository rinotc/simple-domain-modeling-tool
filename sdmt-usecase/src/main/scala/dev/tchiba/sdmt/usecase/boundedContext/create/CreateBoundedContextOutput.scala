package dev.tchiba.sdmt.usecase.boundedContext.create

import dev.tchiba.sdmt.core.models.boundedContext.{BoundedContext, BoundedContextAlias}
import dev.tchiba.sdmt.usecase.Output

sealed abstract class CreateBoundedContextOutput extends Output

object CreateBoundedContextOutput {
  case class Success(newBoundedContext: BoundedContext) extends CreateBoundedContextOutput
  case class ConflictAlias(alias: BoundedContextAlias)  extends CreateBoundedContextOutput
}

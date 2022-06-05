package dev.tchiba.sdmt.usecase.boundedContext.create

import dev.tchiba.arch.usecase.Output
import dev.tchiba.sdmt.core.boundedContext.{BoundedContext, BoundedContextAlias}

sealed abstract class CreateBoundedContextOutput extends Output

object CreateBoundedContextOutput {
  case class Success(newBoundedContext: BoundedContext) extends CreateBoundedContextOutput
  case class ConflictAlias(alias: BoundedContextAlias)  extends CreateBoundedContextOutput
}

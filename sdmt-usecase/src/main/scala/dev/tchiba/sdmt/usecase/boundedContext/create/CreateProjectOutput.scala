package dev.tchiba.sdmt.usecase.boundedContext.create

import dev.tchiba.sdmt.core.models.boundedContext.{BoundedContext, BoundedContextAlias}
import dev.tchiba.sdmt.usecase.Output

sealed abstract class CreateProjectOutput extends Output

object CreateProjectOutput {
  case class Success(newProject: BoundedContext)       extends CreateProjectOutput
  case class ConflictAlias(alias: BoundedContextAlias) extends CreateProjectOutput
}

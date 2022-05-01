package dev.tchiba.sdmt.usecase.boundedContext.create

import dev.tchiba.sdmt.core.models.boundedContext.{Project, BoundedContextAlias}
import dev.tchiba.sdmt.usecase.Output

sealed abstract class CreateProjectOutput extends Output

object CreateProjectOutput {
  case class Success(newProject: Project)              extends CreateProjectOutput
  case class ConflictAlias(alias: BoundedContextAlias) extends CreateProjectOutput
}

package dev.tchiba.sdmt.usecase.boundedContext.update

import dev.tchiba.sdmt.core.models.boundedContext.{Project, BoundedContextId}
import dev.tchiba.sdmt.usecase.Output

sealed abstract class UpdateProjectOutput extends Output

object UpdateProjectOutput {

  case class Success(updatedProject: Project) extends UpdateProjectOutput

  case class NotFound(targetId: BoundedContextId) extends UpdateProjectOutput

  case class ConflictAlias(conflictedProject: Project) extends UpdateProjectOutput
}

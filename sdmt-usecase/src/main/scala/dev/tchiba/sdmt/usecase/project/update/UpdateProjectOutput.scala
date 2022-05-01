package dev.tchiba.sdmt.usecase.project.update

import dev.tchiba.sdmt.core.models.project.{Project, ProjectId}
import dev.tchiba.sdmt.usecase.Output

sealed abstract class UpdateProjectOutput extends Output

object UpdateProjectOutput {

  case class Success(updatedProject: Project) extends UpdateProjectOutput

  case class NotFound(targetId: ProjectId) extends UpdateProjectOutput

  case class ConflictAlias(conflictedProject: Project) extends UpdateProjectOutput
}

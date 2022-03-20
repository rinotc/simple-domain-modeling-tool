package dev.tchiba.sdmt.usecase.project.add

import dev.tchiba.sdmt.core.models.project.{Project, ProjectAlias}
import dev.tchiba.sdmt.usecase.Output

sealed abstract class AddProjectOutput extends Output

object AddProjectOutput {
  case class Success(newProject: Project)       extends AddProjectOutput
  case class ConflictAlias(alias: ProjectAlias) extends AddProjectOutput
}

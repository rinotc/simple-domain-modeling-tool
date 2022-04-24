package dev.tchiba.sdmt.usecase.project.create

import dev.tchiba.sdmt.core.models.project.{Project, ProjectAlias}
import dev.tchiba.sdmt.usecase.Output

sealed abstract class CreateProjectOutput extends Output

object CreateProjectOutput {
  case class Success(newProject: Project)       extends CreateProjectOutput
  case class ConflictAlias(alias: ProjectAlias) extends CreateProjectOutput
}

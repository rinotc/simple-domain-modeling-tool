package domain.usecases.project.add

import domain.models.project.{Project, ProjectAlias}
import domain.usecases.Output

sealed abstract class AddProjectOutput extends Output

object AddProjectOutput {
  case class Success(newProject: Project)       extends AddProjectOutput
  case class ConflictAlias(alias: ProjectAlias) extends AddProjectOutput
}

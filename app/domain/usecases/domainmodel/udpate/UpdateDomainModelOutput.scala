package domain.usecases.domainmodel.udpate

import domain.models.domainmodel.DomainModel
import domain.models.project.{Project, ProjectAlias}
import domain.usecases.Output

sealed abstract class UpdateDomainModelOutput extends Output

object UpdateDomainModelOutput {
  case class Success(updatedDomainModel: DomainModel, project: Project)           extends UpdateDomainModelOutput
  case class NotFoundSuchModel(projectAlias: ProjectAlias, englishName: String)   extends UpdateDomainModelOutput
  case class ConflictEnglishName(projectAlias: ProjectAlias, englishName: String) extends UpdateDomainModelOutput
}

package domain.usecases.domainmodel.add

import domain.models.domainmodel.DomainModel
import domain.models.project.ProjectAlias
import domain.usecases.Output

sealed abstract class AddDomainModelOutput extends Output

object AddDomainModelOutput {
  case class Success(newDomainModel: DomainModel)      extends AddDomainModelOutput
  case class NoSuchProject(projectAlias: ProjectAlias) extends AddDomainModelOutput
  case class ConflictEnglishName(englishName: String)  extends AddDomainModelOutput
}

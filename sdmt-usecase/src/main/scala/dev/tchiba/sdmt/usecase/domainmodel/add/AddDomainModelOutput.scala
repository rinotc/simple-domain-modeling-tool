package dev.tchiba.sdmt.usecase.domainmodel.add

import dev.tchiba.sdmt.core.models.domainmodel.DomainModel
import dev.tchiba.sdmt.core.models.boundedContext.ProjectAlias
import dev.tchiba.sdmt.usecase.Output

sealed abstract class AddDomainModelOutput extends Output

object AddDomainModelOutput {
  case class Success(newDomainModel: DomainModel)      extends AddDomainModelOutput
  case class NoSuchProject(projectAlias: ProjectAlias) extends AddDomainModelOutput
  case class ConflictEnglishName(englishName: String)  extends AddDomainModelOutput
}

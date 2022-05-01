package dev.tchiba.sdmt.usecase.domainmodel.add

import dev.tchiba.sdmt.core.boundedContext.BoundedContextAlias
import dev.tchiba.sdmt.core.domainmodel.DomainModel
import dev.tchiba.sdmt.usecase.Output

sealed abstract class AddDomainModelOutput extends Output

object AddDomainModelOutput {
  case class Success(newDomainModel: DomainModel)             extends AddDomainModelOutput
  case class NoSuchBoundedContext(alias: BoundedContextAlias) extends AddDomainModelOutput
  case class ConflictEnglishName(englishName: String)         extends AddDomainModelOutput
}

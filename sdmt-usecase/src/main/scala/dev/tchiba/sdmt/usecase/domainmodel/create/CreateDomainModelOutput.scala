package dev.tchiba.sdmt.usecase.domainmodel.create

import dev.tchiba.sdmt.core.boundedContext.BoundedContextAlias
import dev.tchiba.sdmt.core.domainmodel.DomainModel
import dev.tchiba.sdmt.usecase.Output

sealed abstract class CreateDomainModelOutput extends Output

object CreateDomainModelOutput {
  case class Success(newDomainModel: DomainModel)             extends CreateDomainModelOutput
  case class NoSuchBoundedContext(alias: BoundedContextAlias) extends CreateDomainModelOutput
  case class ConflictEnglishName(englishName: String)         extends CreateDomainModelOutput
}

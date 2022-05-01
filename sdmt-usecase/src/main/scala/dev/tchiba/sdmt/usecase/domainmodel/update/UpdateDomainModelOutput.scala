package dev.tchiba.sdmt.usecase.domainmodel.update

import dev.tchiba.sdmt.core.boundedContext.{BoundedContext, BoundedContextAlias}
import dev.tchiba.sdmt.core.domainmodel.DomainModel
import dev.tchiba.sdmt.usecase.Output

sealed abstract class UpdateDomainModelOutput extends Output

object UpdateDomainModelOutput {
  case class Success(updatedDomainModel: DomainModel, boundedContext: BoundedContext) extends UpdateDomainModelOutput

  case class NotFoundSuchModel(boundedContextAlias: BoundedContextAlias, englishName: String)
      extends UpdateDomainModelOutput

  case class ConflictEnglishName(boundedContextAlias: BoundedContextAlias, englishName: String)
      extends UpdateDomainModelOutput
}

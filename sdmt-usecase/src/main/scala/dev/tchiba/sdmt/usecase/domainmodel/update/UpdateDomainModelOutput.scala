package dev.tchiba.sdmt.usecase.domainmodel.update

import dev.tchiba.sdmt.core.models.domainmodel.DomainModel
import dev.tchiba.sdmt.core.models.boundedContext.{BoundedContext, BoundedContextAlias}
import dev.tchiba.sdmt.usecase.Output

sealed abstract class UpdateDomainModelOutput extends Output

object UpdateDomainModelOutput {
  case class Success(updatedDomainModel: DomainModel, project: BoundedContext)           extends UpdateDomainModelOutput
  case class NotFoundSuchModel(projectAlias: BoundedContextAlias, englishName: String)   extends UpdateDomainModelOutput
  case class ConflictEnglishName(projectAlias: BoundedContextAlias, englishName: String) extends UpdateDomainModelOutput
}

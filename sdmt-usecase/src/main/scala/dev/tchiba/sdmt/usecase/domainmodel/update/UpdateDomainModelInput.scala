package dev.tchiba.sdmt.usecase.domainmodel.update

import dev.tchiba.sdmt.core.models.boundedContext.BoundedContextAlias
import dev.tchiba.sdmt.usecase.Input

final case class UpdateDomainModelInput(
    projectAlias: BoundedContextAlias,
    englishNameNow: String,
    updatedJapaneseName: String,
    updatedEnglishName: String,
    updatedSpecification: String
) extends Input[UpdateDomainModelOutput]

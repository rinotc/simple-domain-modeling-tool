package dev.tchiba.sdmt.usecase.domainmodel.add

import dev.tchiba.sdmt.core.models.boundedContext.BoundedContextAlias
import dev.tchiba.sdmt.usecase.Input

final case class AddDomainModelInput(
    boundedContextAlias: BoundedContextAlias,
    japaneseName: String,
    englishName: String,
    specification: String
) extends Input[AddDomainModelOutput]

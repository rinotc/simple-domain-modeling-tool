package dev.tchiba.sdmt.usecase.domainmodel.create

import dev.tchiba.sdmt.core.boundedContext.BoundedContextAlias
import dev.tchiba.sdmt.usecase.Input

final case class CreateDomainModelInput(
    boundedContextAlias: BoundedContextAlias,
    japaneseName: String,
    englishName: String,
    specification: String
) extends Input[CreateDomainModelOutput]

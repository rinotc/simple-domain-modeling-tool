package dev.tchiba.sdmt.usecase.domainmodel.create

import dev.tchiba.sdmt.core.boundedContext.BoundedContextId
import dev.tchiba.sdmt.usecase.Input

final case class CreateDomainModelInput(
    boundedContextId: BoundedContextId,
    japaneseName: String,
    englishName: String,
    specification: String
) extends Input[CreateDomainModelOutput]

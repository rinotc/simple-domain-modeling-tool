package dev.tchiba.sdmt.usecase.domainmodel.create

import dev.tchiba.arch.usecase.Input
import dev.tchiba.sdmt.core.boundedContext.BoundedContextId
import dev.tchiba.sdmt.core.domainmodel.{EnglishName, UbiquitousName, Knowledge}

final case class CreateDomainModelInput(
    boundedContextId: BoundedContextId,
    ubiquitousName: UbiquitousName,
    englishName: EnglishName,
    knowledge: Knowledge
) extends Input[CreateDomainModelOutput]

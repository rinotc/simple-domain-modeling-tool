package dev.tchiba.sdmt.usecase.domainmodel.create

import dev.tchiba.sdmt.core.boundedContext.BoundedContextId
import dev.tchiba.sdmt.core.domainmodel.{EnglishName, UbiquitousName, Specification}
import dev.tchiba.sdmt.usecase.Input

final case class CreateDomainModelInput(
    boundedContextId: BoundedContextId,
    ubiquitousName: UbiquitousName,
    englishName: EnglishName,
    specification: Specification
) extends Input[CreateDomainModelOutput]

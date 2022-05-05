package dev.tchiba.sdmt.usecase.domainmodel.create

import dev.tchiba.sdmt.core.boundedContext.BoundedContextId
import dev.tchiba.sdmt.core.domainmodel.{EnglishName, JapaneseName, Specification}
import dev.tchiba.sdmt.usecase.Input

final case class CreateDomainModelInput(
    boundedContextId: BoundedContextId,
    japaneseName: JapaneseName,
    englishName: EnglishName,
    specification: Specification
) extends Input[CreateDomainModelOutput]

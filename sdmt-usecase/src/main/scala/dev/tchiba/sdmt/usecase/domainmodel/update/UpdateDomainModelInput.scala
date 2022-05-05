package dev.tchiba.sdmt.usecase.domainmodel.update

import dev.tchiba.sdmt.core.boundedContext.BoundedContextAlias
import dev.tchiba.sdmt.core.domainmodel.{EnglishName, JapaneseName, Specification}
import dev.tchiba.sdmt.usecase.Input

final case class UpdateDomainModelInput(
    boundedContextAlias: BoundedContextAlias,
    englishNameNow: EnglishName,
    updatedJapaneseName: JapaneseName,
    updatedEnglishName: EnglishName,
    updatedSpecification: Specification
) extends Input[UpdateDomainModelOutput]

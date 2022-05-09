package dev.tchiba.sdmt.usecase.domainmodel.update

import dev.tchiba.sdmt.core.boundedContext.BoundedContextId
import dev.tchiba.sdmt.core.domainmodel.{DomainModelId, EnglishName, JapaneseName, Specification}
import dev.tchiba.sdmt.usecase.Input

final case class UpdateDomainModelInput(
    boundedContextId: BoundedContextId,
    domainModelId: DomainModelId,
    updatedJapaneseName: JapaneseName,
    updatedEnglishName: EnglishName,
    updatedSpecification: Specification
) extends Input[UpdateDomainModelOutput]

package dev.tchiba.sdmt.usecase.domainmodel.update

import dev.tchiba.arch.usecase.Input
import dev.tchiba.sdmt.core.boundedContext.BoundedContextId
import dev.tchiba.sdmt.core.domainmodel.{DomainModelId, EnglishName, UbiquitousName, Knowledge}

final case class UpdateDomainModelInput(
    boundedContextId: BoundedContextId,
    domainModelId: DomainModelId,
    updatedUbiquitousName: UbiquitousName,
    updatedEnglishName: EnglishName,
    updatedKnowledge: Knowledge
) extends Input[UpdateDomainModelOutput]

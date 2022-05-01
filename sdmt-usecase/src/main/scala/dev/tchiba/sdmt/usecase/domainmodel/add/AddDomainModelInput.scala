package dev.tchiba.sdmt.usecase.domainmodel.add

import dev.tchiba.sdmt.core.models.boundedContext.ProjectAlias
import dev.tchiba.sdmt.usecase.Input

final case class AddDomainModelInput(
    projectAlias: ProjectAlias,
    japaneseName: String,
    englishName: String,
    specification: String
) extends Input[AddDomainModelOutput]

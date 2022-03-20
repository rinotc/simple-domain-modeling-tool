package dev.tchiba.sdmt.usecase.domainmodel.update

import dev.tchiba.sdmt.core.models.project.ProjectAlias
import dev.tchiba.sdmt.usecase.Input

final case class UpdateDomainModelInput(
    projectAlias: ProjectAlias,
    englishNameNow: String,
    updatedJapaneseName: String,
    updatedEnglishName: String,
    updatedSpecification: String
) extends Input[UpdateDomainModelOutput]

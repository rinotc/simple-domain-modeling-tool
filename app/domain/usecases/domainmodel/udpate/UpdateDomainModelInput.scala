package domain.usecases.domainmodel.udpate

import domain.models.project.ProjectAlias
import domain.usecases.Input

final case class UpdateDomainModelInput(
    projectAlias: ProjectAlias,
    englishNameNow: String,
    updatedJapaneseName: String,
    updatedEnglishName: String,
    updatedSpecification: String
) extends Input[UpdateDomainModelOutput]

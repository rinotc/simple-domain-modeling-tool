package domain.usecases.domainmodel.add

import domain.models.project.ProjectAlias
import domain.usecases.Input

final case class AddDomainModelInput(
    projectAlias: ProjectAlias,
    japaneseName: String,
    englishName: String,
    specification: String
) extends Input[AddDomainModelOutput]

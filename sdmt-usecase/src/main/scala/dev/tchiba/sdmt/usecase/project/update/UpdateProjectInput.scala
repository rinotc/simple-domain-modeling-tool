package dev.tchiba.sdmt.usecase.project.update

import dev.tchiba.sdmt.core.models.project.{ProjectAlias, ProjectId, ProjectName, ProjectOverview}
import dev.tchiba.sdmt.usecase.Input

case class UpdateProjectInput(
    targetId: ProjectId,
    alias: ProjectAlias,
    name: ProjectName,
    overview: ProjectOverview
) extends Input[UpdateProjectOutput]

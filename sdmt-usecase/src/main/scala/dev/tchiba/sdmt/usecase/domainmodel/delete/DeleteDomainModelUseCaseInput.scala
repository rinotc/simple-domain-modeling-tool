package dev.tchiba.sdmt.usecase.domainmodel.delete

import dev.tchiba.arch.usecase.experimental.Input
import dev.tchiba.sdmt.core.domainmodel.DomainModelId

final case class DeleteDomainModelUseCaseInput(domainModelId: DomainModelId) extends Input

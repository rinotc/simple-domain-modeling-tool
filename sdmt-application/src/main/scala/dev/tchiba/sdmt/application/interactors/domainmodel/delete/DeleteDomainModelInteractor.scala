package dev.tchiba.sdmt.application.interactors.domainmodel.delete

import dev.tchiba.sdmt.core.domainmodel.DomainModelRepository
import dev.tchiba.sdmt.usecase.domainmodel.delete.{
  DeleteDomainModelUseCase,
  DeleteDomainModelUseCaseFailed,
  DeleteDomainModelUseCaseInput,
  DeleteDomainModelUseCaseSucceeded
}

import javax.inject.Inject

class DeleteDomainModelInteractor @Inject() (domainModelRepository: DomainModelRepository)
    extends DeleteDomainModelUseCase {
  override protected def handleImpl(
      input: DeleteDomainModelUseCaseInput
  ): Either[DeleteDomainModelUseCaseFailed, DeleteDomainModelUseCaseSucceeded] = {
    domainModelRepository.delete(input.domainModelId)
    Right(DeleteDomainModelUseCaseSucceeded())
  }
}

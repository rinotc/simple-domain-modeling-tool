package dev.tchiba.sdmt.application.interactors.domainmodel.delete

import dev.tchiba.sdmt.usecase.domainmodel.delete.{
  DeleteDomainModelPolicy,
  DeleteDomainModelUseCaseFailed,
  DeleteDomainModelUseCaseInput
}

import javax.inject.Inject

class DeleteDomainModelUserPolicy @Inject() () extends DeleteDomainModelPolicy {
  override def check(input: DeleteDomainModelUseCaseInput): Either[DeleteDomainModelUseCaseFailed, Unit] = {
    Left(DeleteDomainModelUseCaseFailed.InvalidPolicy(this))
  }
}

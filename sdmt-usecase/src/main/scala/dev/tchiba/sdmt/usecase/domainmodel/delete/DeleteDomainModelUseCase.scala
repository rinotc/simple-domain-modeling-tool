package dev.tchiba.sdmt.usecase.domainmodel.delete

import dev.tchiba.arch.usecase.experimental.UseCase

abstract class DeleteDomainModelUseCase
    extends UseCase[DeleteDomainModelUseCaseInput, DeleteDomainModelUseCaseSucceeded, DeleteDomainModelUseCaseFailed]

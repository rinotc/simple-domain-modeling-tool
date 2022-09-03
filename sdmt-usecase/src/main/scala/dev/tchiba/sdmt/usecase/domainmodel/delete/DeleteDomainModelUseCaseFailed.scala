package dev.tchiba.sdmt.usecase.domainmodel.delete

import dev.tchiba.arch.usecase.experimental.{Failed, Policy}

sealed abstract class DeleteDomainModelUseCaseFailed extends Failed

object DeleteDomainModelUseCaseFailed {
  final case class InvalidPolicy[P <: DeleteDomainModelPolicy](policy: P) extends DeleteDomainModelUseCaseFailed
}

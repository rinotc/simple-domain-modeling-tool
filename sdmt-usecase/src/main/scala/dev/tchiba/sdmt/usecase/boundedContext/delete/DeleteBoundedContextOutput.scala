package dev.tchiba.sdmt.usecase.boundedContext.delete

import dev.tchiba.arch.usecase.Output

sealed abstract class DeleteBoundedContextOutput extends Output

object DeleteBoundedContextOutput {

  final case object Success extends DeleteBoundedContextOutput
}

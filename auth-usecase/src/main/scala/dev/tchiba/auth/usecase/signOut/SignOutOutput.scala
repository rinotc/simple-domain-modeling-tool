package dev.tchiba.auth.usecase.signOut

import dev.tchiba.arch.usecase.Output

sealed abstract class SignOutOutput extends Output

object SignOutOutput {
  final case object Success extends SignOutOutput
}

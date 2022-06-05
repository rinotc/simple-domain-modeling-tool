package dev.tchiba.auth.usecase.signUp

import dev.tchiba.arch.usecase.Output
import dev.tchiba.auth.core.models.AuthInfo
import dev.tchiba.sub.email.EmailAddress

sealed abstract class SignUpOutput extends Output

object SignUpOutput {

  case class Success(authInfo: AuthInfo) extends SignUpOutput

  case class EmailConflictError(email: EmailAddress) extends SignUpOutput
}

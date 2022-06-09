package dev.tchiba.auth.usecase.signUp

import dev.tchiba.arch.usecase.Output
import dev.tchiba.auth.core.accessToken.AccessToken
import dev.tchiba.sub.email.EmailAddress

sealed abstract class SignUpOutput extends Output

object SignUpOutput {

  case class Success(accessToken: AccessToken) extends SignUpOutput

  case class EmailConflictError(email: EmailAddress) extends SignUpOutput
}

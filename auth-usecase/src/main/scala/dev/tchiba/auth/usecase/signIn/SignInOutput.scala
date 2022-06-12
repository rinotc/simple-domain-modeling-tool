package dev.tchiba.auth.usecase.signIn

import dev.tchiba.arch.usecase.Output
import dev.tchiba.auth.core.accessToken.AccessToken
import dev.tchiba.sub.email.EmailAddress

sealed abstract class SignInOutput extends Output

object SignInOutput {
  final case class Success(accessToken: AccessToken) extends SignInOutput

  final case class NotFoundAccount(email: EmailAddress) extends SignInOutput

  final case object InvalidPassword extends SignInOutput
}

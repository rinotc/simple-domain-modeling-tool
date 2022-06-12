package dev.tchiba.auth.usecase.signIn

import dev.tchiba.arch.usecase.Input
import dev.tchiba.auth.core.password.Password
import dev.tchiba.sub.email.EmailAddress

final case class SignInInput(
    email: EmailAddress,
    password: Password
) extends Input[SignInOutput]

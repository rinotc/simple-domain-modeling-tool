package dev.tchiba.auth.usecase.signUp

import dev.tchiba.arch.usecase.Input
import dev.tchiba.auth.core.password.Password
import dev.tchiba.sub.email.EmailAddress

final case class SignUpInput(email: EmailAddress, password: Password) extends Input[SignUpOutput]

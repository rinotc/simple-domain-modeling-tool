package dev.tchiba.auth.usecase.user.edit.email

import dev.tchiba.arch.usecase.Input
import dev.tchiba.auth.core.user.UserId
import dev.tchiba.sub.email.EmailAddress

case class ChangeEmailInput(userId: UserId, email: EmailAddress) extends Input[ChangeEmailOutput]

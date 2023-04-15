package dev.tchiba.auth.usecase.user.edit.email

import dev.tchiba.arch.usecase.Output
import dev.tchiba.auth.core.user.{User, UserId}
import dev.tchiba.sub.email.EmailAddress

sealed abstract class ChangeEmailOutput extends Output

object ChangeEmailOutput {

  final case class Success(user: User) extends ChangeEmailOutput

  final case class NotFoundUser(userId: UserId) extends ChangeEmailOutput

  final case class EmailIsNotUnique(email: EmailAddress) extends ChangeEmailOutput
}

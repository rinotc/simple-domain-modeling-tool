package dev.tchiba.auth.application.interactors.user.edit.email

import dev.tchiba.auth.core.user.UserRepository
import dev.tchiba.auth.core.user.specs.email.unique.UserEmailUniqueChecker
import dev.tchiba.auth.usecase.user.edit.email.{ChangeEmailInput, ChangeEmailOutput, ChangeEmailUseCase}

import javax.inject.Inject

class ChangeEmailInteractor @Inject() (
    userEmailUniqueChecker: UserEmailUniqueChecker,
    userRepository: UserRepository
) extends ChangeEmailUseCase {

  import ChangeEmailOutput._
  override def handle(input: ChangeEmailInput): ChangeEmailOutput = {
    val result = for {
      user                 <- userRepository.findById(input.userId).toRight(NotFoundUser(input.userId))
      emailIsUniqueMessage <- userEmailUniqueChecker.check(input.email).left.map { _ => EmailIsNotUnique(input.email) }
    } yield {
      val emailUpdatedUser = user.changeEmail(emailIsUniqueMessage)
      userRepository.update(emailUpdatedUser)
      Success(emailUpdatedUser)
    }

    result.unwrap
  }
}

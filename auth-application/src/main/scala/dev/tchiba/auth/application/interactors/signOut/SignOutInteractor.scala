package dev.tchiba.auth.application.interactors.signOut

import dev.tchiba.auth.core.accessToken.AccessTokenService
import dev.tchiba.auth.usecase.signOut.{SignOutInput, SignOutOutput, SignOutUseCase}

import javax.inject.Inject

final class SignOutInteractor @Inject() (
    accessTokenService: AccessTokenService
) extends SignOutUseCase {
  override def handle(input: SignOutInput): SignOutOutput = {
    accessTokenService.delete(input.accessToken)
    SignOutOutput.Success
  }
}

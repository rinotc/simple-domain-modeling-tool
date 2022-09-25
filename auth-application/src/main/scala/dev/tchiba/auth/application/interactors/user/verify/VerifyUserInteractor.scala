package dev.tchiba.auth.application.interactors.user.verify

import dev.tchiba.auth.application.services.AuthInfoUserLinker
import dev.tchiba.auth.core.accessToken.AccessTokenService
import dev.tchiba.auth.usecase.user.verify.{VerifyUserInput, VerifyUserOutput, VerifyUserUseCase}

import javax.inject.Inject

final class VerifyUserInteractor @Inject() (
    accessTokenService: AccessTokenService,
    authInfoUserLinker: AuthInfoUserLinker
) extends VerifyUserUseCase {
  override def handle(input: VerifyUserInput): VerifyUserOutput = {

    val result = for {
      authId <- accessTokenService.verify(input.token).left.map { _ => VerifyUserOutput.UnLogin(input.token) }
      user   <- authInfoUserLinker.link(authId).toRight(VerifyUserOutput.NotFoundUser(authId))
    } yield VerifyUserOutput.Verified(user)

    result.unwrap
  }
}

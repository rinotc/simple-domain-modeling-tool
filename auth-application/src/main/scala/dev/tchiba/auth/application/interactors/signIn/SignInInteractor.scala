package dev.tchiba.auth.application.interactors.signIn

import dev.tchiba.auth.core.accessToken.{AccessToken, AccessTokenService}
import dev.tchiba.auth.core.authInfo.{AuthInfo, AuthInfoRepository}
import dev.tchiba.auth.core.password.Password
import dev.tchiba.auth.usecase.signIn.{SignInInput, SignInOutput, SignInUseCase}

import javax.inject.Inject

final class SignInInteractor @Inject() (
    authInfoRepository: AuthInfoRepository,
    accessTokenService: AccessTokenService
) extends SignInUseCase {
  override def handle(input: SignInInput): SignInOutput = {
    val result = for {
      authInfo    <- authInfoRepository.findBy(input.email).toRight(SignInOutput.NotFoundAccount(input.email))
      accessToken <- verifyPassword(authInfo, input.password)
    } yield {
      accessTokenService.register(accessToken, authInfo.id)
      SignInOutput.Success(accessToken)
    }

    result.unwrap
  }

  private def verifyPassword(authInfo: AuthInfo, password: Password) = {
    Either.cond(
      authInfo.validatePassword(password),
      AccessToken.generate,
      SignInOutput.InvalidPassword
    )
  }
}

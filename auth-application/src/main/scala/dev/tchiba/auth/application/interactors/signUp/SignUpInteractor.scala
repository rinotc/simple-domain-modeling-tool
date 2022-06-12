package dev.tchiba.auth.application.interactors.signUp

import dev.tchiba.auth.core.accessToken.{AccessToken, AccessTokenService}
import dev.tchiba.auth.core.authInfo.{AuthInfo, AuthInfoRepository}
import dev.tchiba.auth.usecase.signUp.{SignUpInput, SignUpOutput, SignUpUseCase}

import javax.inject.Inject

final class SignUpInteractor @Inject() (
    authInfoRepository: AuthInfoRepository,
    accessTokenService: AccessTokenService
) extends SignUpUseCase {

  override def handle(input: SignUpInput): SignUpOutput = {
    authInfoRepository.findBy(input.email) match {
      case Some(_) => SignUpOutput.EmailConflictError(input.email)
      case None =>
        val newAuthInfo = AuthInfo.create(input.email, input.password)
        authInfoRepository.insert(newAuthInfo)
        val newAccessToken = AccessToken.generate
        accessTokenService.register(newAccessToken, newAuthInfo.id)
        SignUpOutput.Success(newAccessToken)
    }
  }
}

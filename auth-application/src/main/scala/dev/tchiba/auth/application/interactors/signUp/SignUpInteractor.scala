package dev.tchiba.auth.application.interactors.signUp

import dev.tchiba.auth.core.accessToken.{AccessToken, AccessTokenService}
import dev.tchiba.auth.core.authInfo.{AuthInfo, AuthInfoRepository}
import dev.tchiba.auth.core.user.{User, UserRepository}
import dev.tchiba.auth.usecase.signUp.{SignUpInput, SignUpOutput, SignUpUseCase}

import javax.inject.Inject

final class SignUpInteractor @Inject() (
    authInfoRepository: AuthInfoRepository,
    accessTokenService: AccessTokenService,
    userRepository: UserRepository
) extends SignUpUseCase {

  override def handle(input: SignUpInput): SignUpOutput = {
    authInfoRepository.findBy(input.email) match {
      case Some(_) => SignUpOutput.EmailConflictError(input.email)
      case None =>
        val newUser     = User.create(input.email)
        val newAuthInfo = AuthInfo.create(newUser.id, input.password)
        userRepository.insert(newUser)
        authInfoRepository.insert(newAuthInfo)
        val newAccessToken = AccessToken.generate
        accessTokenService.register(newAccessToken, newAuthInfo.id)
        SignUpOutput.Success(newAccessToken)
    }
  }
}

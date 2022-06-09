package dev.tchiba.auth.application.interactors.signUp

import com.typesafe.config.ConfigFactory
import dev.tchiba.auth.core.accessToken.{AccessToken, AccessTokenService}
import dev.tchiba.auth.core.authInfo.{AuthInfo, AuthInfoRepository}
import dev.tchiba.auth.usecase.signUp.{SignUpInput, SignUpOutput, SignUpUseCase}

import java.time.LocalDateTime
import javax.inject.Inject

final class SignUpInteractor @Inject() (
    authInfoRepository: AuthInfoRepository,
    accessTokenService: AccessTokenService
) extends SignUpUseCase {

  private val config              = ConfigFactory.load()
  private val tokenExpiryDuration = config.getDuration("dev.tchiba.auth.token.expiry")

  override def handle(input: SignUpInput): SignUpOutput = {
    authInfoRepository.findBy(input.email) match {
      case Some(_) => SignUpOutput.EmailConflictError(input.email)
      case None =>
        val newAuthInfo = AuthInfo.create(input.email, input.password)
        authInfoRepository.insert(newAuthInfo)
        val newAccessToken = AccessToken.generate(newAuthInfo)

        val expiry = LocalDateTime.now().plusSeconds(tokenExpiryDuration.toSeconds)

        accessTokenService.register(newAccessToken, expiry)
        SignUpOutput.Success(newAccessToken)
    }
  }
}

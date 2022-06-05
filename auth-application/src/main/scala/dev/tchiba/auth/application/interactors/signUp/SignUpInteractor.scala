package dev.tchiba.auth.application.interactors.signUp

import dev.tchiba.auth.core.models.{AuthInfo, AuthInfoRepository}
import dev.tchiba.auth.usecase.signUp.{SignUpInput, SignUpOutput, SignUpUseCase}

import javax.inject.Inject

final class SignUpInteractor @Inject() (
    authInfoRepository: AuthInfoRepository
) extends SignUpUseCase {
  override def handle(input: SignUpInput): SignUpOutput = {
    authInfoRepository.findBy(input.email) match {
      case Some(_) => SignUpOutput.EmailConflictError(input.email)
      case None =>
        val newAuthInfo = AuthInfo.create(input.email, input.password)
        authInfoRepository.insert(newAuthInfo)
        SignUpOutput.Success(newAuthInfo)
    }
  }
}

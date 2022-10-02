package dev.tchiba.auth.module

import com.google.inject.AbstractModule
import dev.tchiba.auth.application.interactors.signIn.SignInInteractor
import dev.tchiba.auth.application.interactors.signOut.SignOutInteractor
import dev.tchiba.auth.application.interactors.signUp.SignUpInteractor
import dev.tchiba.auth.application.interactors.user.verify.VerifyUserInteractor
import dev.tchiba.auth.application.services.AuthInfoUserLinker
import dev.tchiba.auth.core.accessToken.AccessTokenService
import dev.tchiba.auth.core.authInfo.AuthInfoRepository
import dev.tchiba.auth.infra.application.services.JdbcAuthInfoUserLinker
import dev.tchiba.auth.infra.core.accessToken.MemcachedAccessTokenService
import dev.tchiba.auth.infra.core.authInfo.JdbcAuthInfoRepository
import dev.tchiba.auth.usecase.signIn.SignInUseCase
import dev.tchiba.auth.usecase.signOut.SignOutUseCase
import dev.tchiba.auth.usecase.signUp.SignUpUseCase
import dev.tchiba.auth.usecase.user.verify.VerifyUserUseCase
import net.codingwell.scalaguice.ScalaModule

class AuthModule extends AbstractModule with ScalaModule {
  override def configure(): Unit = {
    bind[SignUpUseCase].to[SignUpInteractor]
    bind[SignInUseCase].to[SignInInteractor]
    bind[SignOutUseCase].to[SignOutInteractor]
    bind[VerifyUserUseCase].to[VerifyUserInteractor]

    bind[AuthInfoRepository].to[JdbcAuthInfoRepository]

    bind[AccessTokenService].to[MemcachedAccessTokenService]
    bind[AuthInfoUserLinker].to[JdbcAuthInfoUserLinker]
  }
}

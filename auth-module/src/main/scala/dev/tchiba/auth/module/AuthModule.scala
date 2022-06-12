package dev.tchiba.auth.module

import com.google.inject.AbstractModule
import dev.tchiba.auth.application.interactors.signUp.SignUpInteractor
import dev.tchiba.auth.core.accessToken.AccessTokenService
import dev.tchiba.auth.core.authInfo.AuthInfoRepository
import dev.tchiba.auth.infra.core.accessToken.MemcachedAccessTokenService
import dev.tchiba.auth.infra.core.authInfo.JdbcAuthInfoRepository
import dev.tchiba.auth.usecase.signUp.SignUpUseCase
import net.codingwell.scalaguice.ScalaModule

class AuthModule extends AbstractModule with ScalaModule {
  override def configure(): Unit = {
    bind[SignUpUseCase].to[SignUpInteractor]

    bind[AuthInfoRepository].to[JdbcAuthInfoRepository]

    bind[AccessTokenService].to[MemcachedAccessTokenService]
  }
}

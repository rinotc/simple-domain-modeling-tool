package dev.tchiba.auth.core.accessToken

import dev.tchiba.arch.ddd.ApplicationService
import dev.tchiba.auth.core.authInfo.AuthId

trait AccessTokenService extends ApplicationService {

  def register(accessToken: AccessToken, authId: AuthId): Unit

  def verify(accessToken: AccessToken): Either[Unit, AuthId]

  def delete(accessToken: AccessToken): Unit
}

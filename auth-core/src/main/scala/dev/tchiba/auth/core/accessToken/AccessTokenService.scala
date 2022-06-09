package dev.tchiba.auth.core.accessToken

import dev.tchiba.arch.ddd.ApplicationService

import java.time.LocalDateTime

trait AccessTokenService extends ApplicationService {

  def register(accessToken: AccessToken, expiry: LocalDateTime): Unit

  def verify(accessToken: AccessToken): Boolean
}

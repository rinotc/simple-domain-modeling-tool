package dev.tchiba.auth.core.accessToken

import dev.tchiba.arch.ddd.ValueObject

import java.util.UUID

/**
 * アクセストークン
 */
final case class AccessToken private (token: String) extends ValueObject

object AccessToken {

  /**
   * アクセストークンを生成する
   *
   * @return
   */
  def generate: AccessToken = {
    val token = UUID.randomUUID().toString
    apply(token)
  }
}

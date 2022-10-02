package dev.tchiba.auth.core.accessToken

import dev.tchiba.arch.ddd.ValueObject

import java.util.UUID
import scala.util.Try

/**
 * アクセストークン
 */
final case class AccessToken private (token: String) extends ValueObject {
  require(mustBeUUIDString, s"token must be UUID string, but value is $token")

  def asUUID: UUID = UUID.fromString(token)

  private def mustBeUUIDString: Boolean = Try(UUID.fromString(token)).isSuccess
}
object AccessToken {

  def apply(uuid: UUID): AccessToken = apply(uuid.toString)

  /**
   * アクセストークンを生成する
   *
   * @return
   */
  def generate: AccessToken = {
    val token = UUID.randomUUID()
    apply(token)
  }

  def validate(token: String): Either[String, AccessToken] =
    Try(UUID.fromString(token)).toEither.left.map(_.getMessage).map(apply)
}

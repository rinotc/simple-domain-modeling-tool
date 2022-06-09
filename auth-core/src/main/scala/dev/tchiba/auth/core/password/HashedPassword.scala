package dev.tchiba.auth.core.password

import dev.tchiba.arch.ddd.ValueObject

/**
 * ハッシュ済みパスワード
 *
 * @param hashedPassword ハッシュ済みパスワード
 * @param salt           ソルト
 */
final class HashedPassword private (
    val hashedPassword: String,
    val salt: String
) extends ValueObject {

  override def toString = s"HashedPassword(hashedPassword = ********, salt = ****)"
}

object HashedPassword {
  def apply(hashedPassword: String, salt: String) = new HashedPassword(hashedPassword, salt)
}

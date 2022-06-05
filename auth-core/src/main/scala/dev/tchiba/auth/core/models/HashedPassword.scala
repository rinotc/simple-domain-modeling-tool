package dev.tchiba.auth.core.models

import dev.tchiba.arch.ddd.ValueObject

/**
 * ハッシュ済みパスワード
 *
 * @param hashedPassword ハッシュ済みパスワード
 * @param salt           ソルト
 */
final case class HashedPassword private (hashedPassword: String, salt: String) extends ValueObject

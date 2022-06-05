package dev.tchiba.auth.core.models

import dev.tchiba.arch.ddd.ValueObject

/**
 * ハッシュ済みパスワード
 * @param value ハッシュ済みパスワード
 */
final case class HashedPassword private (value: String) extends ValueObject

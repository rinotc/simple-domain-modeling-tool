package dev.tchiba.auth.core.models

import java.util.UUID

/**
 * 認証用のID
 */
final class AuthId(val value: UUID) {

  override def equals(other: Any): Boolean = other match {
    case that: AuthId => value == that.value
    case _            => false
  }

  override def hashCode(): Int = 31 * value.##

  override def toString = s"AuthId($value)"
}

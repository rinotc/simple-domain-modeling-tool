package dev.tchiba.auth.core.authInfo

import dev.tchiba.arch.ddd.{EntityId, EntityIdCompanionUUID}

import java.util.UUID

/**
 * 認証用のID
 */
final class AuthId(val value: UUID) extends EntityId[UUID] {

  override def equals(other: Any): Boolean = other match {
    case that: AuthId => value == that.value
    case _            => false
  }

  override def hashCode(): Int = 31 * value.##

  override def toString = s"AuthId($value)"
}

object AuthId extends EntityIdCompanionUUID[AuthId] {
  def apply(value: UUID): AuthId = new AuthId(value)
}

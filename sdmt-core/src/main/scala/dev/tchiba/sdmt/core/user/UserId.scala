package dev.tchiba.sdmt.core.user

import dev.tchiba.arch.ddd.EntityId
import java.util.UUID

final class UserId(val value: UUID) extends EntityId[UUID] {

  override def equals(other: Any): Boolean = other match {
    case that: UserId => value == that.value
    case _            => false
  }

  override def hashCode(): Int = 31 * value.##

  override def toString = s"UserId($value)"
}

object UserId {
  def apply(value: UUID) = new UserId(value)

  def generate() = new UserId(UUID.randomUUID())

  def fromString(value: String) = new UserId(UUID.fromString(value))
}

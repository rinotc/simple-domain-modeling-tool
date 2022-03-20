package dev.tchiba.sdmt.core.models.domainmodel

import dev.tchiba.sdmt.core.EntityId

import java.util.UUID

final class DomainModelId(val value: UUID) extends EntityId[UUID] {

  def asString: String = value.toString

  override def equals(other: Any): Boolean = other match {
    case that: DomainModelId => value == that.value
    case _                   => false
  }

  override def hashCode(): Int = 31 * value.##

  override def toString = s"DomainModelId($value)"
}

object DomainModelId {

  def apply(value: UUID): DomainModelId = new DomainModelId(value)

  def fromString(value: String): DomainModelId = apply(UUID.fromString(value))

  def generate: DomainModelId = apply(UUID.randomUUID())
}

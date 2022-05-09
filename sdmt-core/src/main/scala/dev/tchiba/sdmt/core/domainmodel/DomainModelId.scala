package dev.tchiba.sdmt.core.domainmodel

import dev.tchiba.sdmt.core.{EntityId, EntityIdCompanion}

import java.util.UUID

final class DomainModelId(val value: UUID) extends EntityId[UUID] {

  override def equals(other: Any): Boolean = other match {
    case that: DomainModelId => value == that.value
    case _                   => false
  }

  override def hashCode(): Int = 31 * value.##

  override def toString = s"DomainModelId($value)"
}

object DomainModelId extends EntityIdCompanion[UUID, DomainModelId] {
  override def apply(value: UUID): DomainModelId = new DomainModelId(value)
}

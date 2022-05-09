package dev.tchiba.sdmt.core.boundedContext

import dev.tchiba.sdmt.core.{EntityId, EntityIdCompanionUUID}

import java.util.UUID

/**
 * 境界づけられたコンテキストID
 */
final class BoundedContextId(val value: UUID) extends EntityId[UUID] {

  override def equals(other: Any): Boolean = other match {
    case that: BoundedContextId => value == that.value
    case _                      => false
  }

  override def hashCode(): Int = 31 * value.##

  override def toString = s"BoundedContextId($value)"
}

object BoundedContextId extends EntityIdCompanionUUID[BoundedContextId] {
  override def apply(value: UUID): BoundedContextId = new BoundedContextId(value)
}

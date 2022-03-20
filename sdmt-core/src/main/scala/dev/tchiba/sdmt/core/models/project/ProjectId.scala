package dev.tchiba.sdmt.core.models.project

import dev.tchiba.sdmt.core.EntityId

import java.util.UUID

final class ProjectId(val value: UUID) extends EntityId[UUID] {

  def asString: String = value.toString

  override def equals(other: Any): Boolean = other match {
    case that: ProjectId => value == that.value
    case _               => false
  }

  override def hashCode(): Int = 31 * value.##

  override def toString = s"ProjectId($value)"
}

object ProjectId {

  def apply(value: UUID): ProjectId = new ProjectId(value)

  def fromString(value: String): ProjectId = apply(UUID.fromString(value))

  def generate: ProjectId = apply(UUID.randomUUID())
}

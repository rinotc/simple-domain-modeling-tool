package dev.tchiba.sdmt.core.models.project

import dev.tchiba.sdmt.core.EntityId

import java.util.UUID
import scala.util.{Failure, Success, Try}

final class ProjectId(val value: UUID) extends EntityId[UUID] {

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

  def validate(value: String): Either[String, ProjectId] = Try(fromString(value)) match {
    case Success(projectId)                   => Right(projectId)
    case Failure(e: IllegalArgumentException) => Left(e.getMessage)
    case Failure(e)                           => throw e
  }

  def generate: ProjectId = apply(UUID.randomUUID())
}

package dev.tchiba.sdmt.core.models.boundedContext

import dev.tchiba.sdmt.core.EntityId

import java.util.UUID
import scala.util.{Failure, Success, Try}

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

object BoundedContextId {

  def apply(value: UUID): BoundedContextId = new BoundedContextId(value)

  def fromString(value: String): BoundedContextId = apply(UUID.fromString(value))

  def validate(value: String): Either[String, BoundedContextId] = Try(fromString(value)) match {
    case Success(projectId)                   => Right(projectId)
    case Failure(e: IllegalArgumentException) => Left(e.getMessage)
    case Failure(e)                           => throw e
  }

  def generate: BoundedContextId = apply(UUID.randomUUID())
}

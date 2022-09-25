package dev.tchiba.arch.ddd

import java.util.UUID
import scala.util.{Failure, Success, Try}

trait EntityIdCompanionUUID[E <: EntityId[UUID]] extends EntityIdCompanion[UUID, E] {

  override type ValidateValueType = String

  def fromString(value: String): E = apply(UUID.fromString(value))

  def validate(value: String): Either[String, E] = Try(UUID.fromString(value)) match {
    case Success(id)                          => Right(apply(id))
    case Failure(e: IllegalArgumentException) => Left(e.getMessage)
    case Failure(e)                           => throw e
  }

  override def generate(): E = apply(UUID.randomUUID())
}

package dev.tchiba.sdmt.core

import java.util.UUID
import scala.util.{Failure, Success, Try}

trait EntityIdCompanion[U, E <: EntityId[U]] {

  def apply(value: U): E

  def fromString(value: String)(implicit ev: U =:= UUID): E = apply(UUID.fromString(value).asInstanceOf[U])

  def validate(value: String)(implicit ev: U =:= UUID): Either[String, E] = Try(UUID.fromString(value)) match {
    case Success(id)                          => Right(apply(id.asInstanceOf[U]))
    case Failure(e: IllegalArgumentException) => Left(e.getMessage)
    case Failure(e)                           => throw e
  }

  def generate(implicit ev: U =:= UUID): E = apply(UUID.randomUUID().asInstanceOf[U])
}

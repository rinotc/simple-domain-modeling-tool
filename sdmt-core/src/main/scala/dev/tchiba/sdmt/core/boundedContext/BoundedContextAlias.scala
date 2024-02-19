package dev.tchiba.sdmt.core.boundedContext

import dev.tchiba.arch.ddd.ValueObject

/**
 * 境界づけられたコンテキストのエイリアス
 *
 * システム内で一意である必要がある
 */
final case class BoundedContextAlias private (value: String) extends ValueObject {

  import BoundedContextAlias._

  requirements(value).left.foreach(message => throw new IllegalArgumentException(message))
}

object BoundedContextAlias {
  def validate(value: String): Either[String, BoundedContextAlias] =
    requirements(value).map(_ => apply(value))

  private def requirements(value: String): Either[String, Unit] = for {
    _ <- mustNotEmpty(value)
    _ <- mustLessThan32Length(value)
    _ <- mustOnlyAlphanumerical(value)
  } yield ()

  private def mustNotEmpty(value: String): Either[String, Unit] =
    Either.cond(value.nonEmpty, (), "BoundedContextAlias must not be empty")

  private def mustLessThan32Length(value: String): Either[String, Unit] =
    Either.cond(value.lengthIs <= 32, (), "BoundedContextAlias must be less than 32 characters")

  private def mustOnlyAlphanumerical(value: String): Either[String, Unit] =
    Either.cond("^[0-9a-zA-Z]{1,32}$".r.matches(value), (), "BoundedContextAlias must be alphanumerical")
}

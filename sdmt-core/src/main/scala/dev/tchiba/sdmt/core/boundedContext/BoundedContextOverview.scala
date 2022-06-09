package dev.tchiba.sdmt.core.boundedContext

import dev.tchiba.arch.ddd.ValueObject

/**
 * 境界づけられたコンテキストの概要
 */
final class BoundedContextOverview(val value: String) extends ValueObject {

  import BoundedContextOverview._

  require(mustLessThan500Length(value), mustLessThan500LengthMessage(value))

  override def equals(other: Any): Boolean = other match {
    case that: BoundedContextOverview => value == that.value
    case _                            => false
  }

  override def hashCode(): Int = 31 * value.##

  override def toString = s"BoundedContextOverview($value)"
}

object BoundedContextOverview {
  def apply(value: String): BoundedContextOverview = new BoundedContextOverview(value)

  def validate(value: String): Either[String, BoundedContextOverview] = Either.cond(
    mustLessThan500Length(value),
    apply(value),
    mustLessThan500LengthMessage(value)
  )

  private def mustLessThan500Length(value: String): Boolean = value.lengthIs <= 500

  private def mustLessThan500LengthMessage(value: String): String =
    s"BoundedContextOverview must less than 500 length. but ${value.length}"
}

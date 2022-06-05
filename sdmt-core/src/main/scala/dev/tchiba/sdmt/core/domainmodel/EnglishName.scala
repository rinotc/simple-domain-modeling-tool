package dev.tchiba.sdmt.core.domainmodel

import dev.tchiba.arch.ddd.ValueObject

/**
 * ドメインモデルの英語名
 *
 * @param value 1文字以上100文字以下の英語もしくは数字文字列。半角スペースを含まない。
 */
final class EnglishName private (val value: String) extends ValueObject {

  import EnglishName._

  require(requirement(value), requirementErrorMessage(value))

  override def equals(other: Any): Boolean = other match {
    case that: EnglishName => value == that.value
    case _                 => false
  }

  override def hashCode(): Int = 31 * value.##

  override def toString = s"EnglishName($value)"
}

object EnglishName {
  def apply(value: String) = new EnglishName(value)

  def validate(value: String): Either[String, EnglishName] =
    Either.cond(requirement(value), apply(value), requirementErrorMessage(value))

  private def requirementErrorMessage(value: String): String =
    s"EnglishName must between 1 to 100 length and only use alphabet or numerical, but value is $value"

  private def requirement(value: String): Boolean = valueLengthMustBe1To100(value) && mustOnlyAlphanumerical(value)

  private def valueLengthMustBe1To100(value: String): Boolean = value.nonEmpty && value.lengthIs <= 100

  private def mustOnlyAlphanumerical(value: String): Boolean = "[a-zA-Z\\d]{1,100}".r.matches(value)
}

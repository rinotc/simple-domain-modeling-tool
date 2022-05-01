package dev.tchiba.sdmt.core.sub.url

import dev.tchiba.sdmt.core.ValueObject

final class Url(val value: String) extends ValueObject {
  import Url._

  require(urlRegex.matches(value), s"$value is invalid url.")

  override def equals(other: Any): Boolean = other match {
    case that: Url => value == that.value
    case _         => false
  }

  override def hashCode(): Int = 31 * value.##

  override def toString = s"Url($value)"
}

object Url {

  private def urlRegex =
    "^(http://www\\.|https://www\\.|http://|https://)?[a-z0-9]+([\\-.]{1}[a-z0-9]+)*\\.[a-z]{2,5}(:[0-9]{1,5})?(/.*)?$".r

  def apply(value: String) = new Url(value)
}

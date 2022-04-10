package dev.tchiba.sdmt.core

import java.util.UUID

trait EntityId[A] {
  def value: A

  def string(implicit ev: A =:= UUID): String = value.toString
}

package dev.tchiba.arch.ddd

import java.util.UUID

trait EntityId[A] {
  def value: A

  def string(implicit ev: A =:= UUID): String = value.toString
}

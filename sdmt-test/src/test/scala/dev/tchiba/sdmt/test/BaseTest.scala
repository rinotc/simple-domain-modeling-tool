package dev.tchiba.sdmt.test

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.{EitherValues, Inside, OptionValues}

trait BaseTest extends AnyWordSpec with Matchers with OptionValues with EitherValues with Inside {
  implicit protected class ValueTestContextOps[A](value: A) {
    def some: Option[A] = Some(value)

    def left[R]: Either[A, R] = Left(value)

    def right[L]: Either[L, A] = Right(value)
  }

  val unit: Unit = ()
}

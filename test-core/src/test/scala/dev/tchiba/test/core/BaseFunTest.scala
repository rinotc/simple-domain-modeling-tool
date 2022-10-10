package dev.tchiba.test.core

import org.scalatest.diagrams.Diagrams
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.{EitherValues, Inside, OptionValues}

trait BaseFunTest extends AnyFunSpec with Matchers with OptionValues with EitherValues with Inside with Diagrams {
  implicit protected class ValueTestContextOps[A](value: A) {
    def some: Option[A] = Some(value)

    def left[R]: Either[A, R] = Left(value)

    def right[L]: Either[L, A] = Right(value)
  }

  final val unit: Unit = ()
}

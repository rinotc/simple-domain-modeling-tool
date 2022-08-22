package dev.tchiba.arch.extensions

trait EitherExtensions {

  implicit class SameTypeEitherExtension[O](either: Either[O, O]) {
    def unwrap: O = either match {
      case Left(value)  => value
      case Right(value) => value
    }
  }

  implicit class EitherValueExtension[A](value: A) {
    def right[B]: Either[B, A] = Right(value)

    def left[B]: Either[A, B] = Left(value)
  }
}

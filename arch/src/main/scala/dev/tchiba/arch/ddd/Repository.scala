package dev.tchiba.arch.ddd

trait Repository[A <: Aggregate] {

  def unit(): Unit = ()

  implicit protected class ValueRepositoryExtension[T](value: T) {
    def right[L]: Either[L, T] = Right(value)

    def left[R]: Either[T, R] = Left(value)
  }
}

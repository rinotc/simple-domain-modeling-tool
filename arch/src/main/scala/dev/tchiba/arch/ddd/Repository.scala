package dev.tchiba.arch.ddd

trait Repository[A <: Aggregate] {

  //noinspection UnitMethodIsParameterless
  final protected val unit: Unit = ()

  implicit protected class ValueRepositoryExtension[T](value: T) {
    final def right[L]: Either[L, T] = Right(value)

    final def left[R]: Either[T, R] = Left(value)
  }
}

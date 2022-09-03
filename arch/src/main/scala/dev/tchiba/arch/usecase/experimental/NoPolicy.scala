package dev.tchiba.arch.usecase.experimental

object NoPolicy extends Policy[Nothing, Nothing] {
  override def check(input: Nothing): Either[Nothing, Unit] = Right(())
}

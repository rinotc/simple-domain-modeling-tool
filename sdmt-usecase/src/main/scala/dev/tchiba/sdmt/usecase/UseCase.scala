package dev.tchiba.sdmt.usecase

import dev.tchiba.sdmt.core.ApplicationService

abstract class UseCase[TInput <: Input[TOutput], TOutput <: Output] extends ApplicationService {
  def handle(input: TInput): TOutput

  implicit protected class EitherUseCaseContextExtensions[O <: Output](either: Either[O, O]) {
    def unwrap: O = either match {
      case Left(value)  => value
      case Right(value) => value
    }
  }
}

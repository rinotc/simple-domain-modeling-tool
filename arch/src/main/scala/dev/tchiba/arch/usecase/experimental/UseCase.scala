package dev.tchiba.arch.usecase.experimental

import dev.tchiba.arch.extensions.EitherExtensions

trait UseCase[TInput <: Input, TFailed <: Failed, TSucceeded <: Succeeded] extends EitherExtensions {

  def handle[TPolicy <: Policy[TInput, TFailed]](
      input: TInput,
      policy: TPolicy = NoPolicy
  ): Either[TFailed, TSucceeded] = {
    for {
      _ <- policy.check(input)
      o <- handleImpl(input)
    } yield o
  }

  protected def handleImpl(input: TInput): Either[TFailed, TSucceeded]
}

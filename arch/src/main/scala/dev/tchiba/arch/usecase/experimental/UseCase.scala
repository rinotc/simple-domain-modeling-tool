package dev.tchiba.arch.usecase.experimental

import dev.tchiba.arch.extensions.EitherExtensions

trait UseCase[TInput <: Input, TSucceeded <: Succeeded, TFailed <: Failed] extends EitherExtensions {

  def handle[TPolicy <: Policy[TInput, TFailed]](input: TInput, policy: TPolicy): Either[TFailed, TSucceeded] = {
    for {
      _ <- policy.check(input)
      o <- handleImpl(input)
    } yield o
  }

  protected def handleImpl(input: TInput): Either[TFailed, TSucceeded]
}

package dev.tchiba.arch.usecase.experimental

trait UseCase[TInput <: Input, TSucceeded <: Succeeded, TFailed <: Failed] {

  def handle[TPolicy <: Policy[TInput, TFailed]](input: TInput, policy: TPolicy): Either[TFailed, TSucceeded] = {
    for {
      _ <- policy.check(input)
      o <- handleImpl(input)
    } yield o
  }

  protected def handleImpl(input: TInput): Either[TFailed, TSucceeded]
}

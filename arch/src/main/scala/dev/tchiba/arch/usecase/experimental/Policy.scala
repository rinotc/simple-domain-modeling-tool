package dev.tchiba.arch.usecase.experimental

abstract class Policy[TInput <: Input, TFailed <: Failed] {
  def check(input: TInput): Either[TFailed, Unit]
}

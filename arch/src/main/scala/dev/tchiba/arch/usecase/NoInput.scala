package dev.tchiba.arch.usecase

final class NoInput[TOutput <: Output] extends Input[TOutput]

object NoInput {
  def apply[TOutput <: Output] = new NoInput[TOutput]
}

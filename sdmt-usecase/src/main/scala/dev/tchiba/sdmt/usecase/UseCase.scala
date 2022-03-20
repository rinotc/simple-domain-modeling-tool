package dev.tchiba.sdmt.usecase

import dev.tchiba.sdmt.core.ApplicationService

abstract class UseCase[TInput <: Input[TOutput], TOutput <: Output] extends ApplicationService {
  def handle(input: TInput): TOutput
}

package domain.usecases

import domain.ApplicationService

abstract class UseCase[TInput <: Input[TOutput], TOutput <: Output] extends ApplicationService {
  def handle(input: TInput): TOutput
}

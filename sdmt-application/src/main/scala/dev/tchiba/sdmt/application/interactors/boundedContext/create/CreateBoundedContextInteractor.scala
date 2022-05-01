package dev.tchiba.sdmt.application.interactors.boundedContext.create

import dev.tchiba.sdmt.core.boundedContext.{BoundedContext, BoundedContextRepository}
import dev.tchiba.sdmt.usecase.boundedContext.create.{
  CreateBoundedContextInput,
  CreateBoundedContextOutput,
  CreateBoundedContextUseCase
}

import javax.inject.Inject

class CreateBoundedContextInteractor @Inject() (
    boundedContextRepository: BoundedContextRepository
) extends CreateBoundedContextUseCase {
  override def handle(input: CreateBoundedContextInput): CreateBoundedContextOutput = {
    boundedContextRepository.findByAlias(input.alias) match {
      case Some(_) => CreateBoundedContextOutput.ConflictAlias(input.alias)
      case None =>
        val newBoundedContext = BoundedContext.create(input.alias, input.name, input.overview)
        boundedContextRepository.insert(newBoundedContext)
        CreateBoundedContextOutput.Success(newBoundedContext)
    }
  }
}

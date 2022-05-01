package dev.tchiba.sdmt.application.interactors.boundedContext.create

import dev.tchiba.sdmt.core.models.boundedContext.{BoundedContext, BoundedContextRepository}
import dev.tchiba.sdmt.usecase.boundedContext.create.{
  CreateBoundedContextInput,
  CreateBoundedContextOutput,
  CreateBoundedContextUseCase
}

import javax.inject.Inject

class CreateBoundedContextInteractor @Inject() (
    projectRepository: BoundedContextRepository
) extends CreateBoundedContextUseCase {
  override def handle(input: CreateBoundedContextInput): CreateBoundedContextOutput = {
    projectRepository.findByAlias(input.alias) match {
      case Some(_) => CreateBoundedContextOutput.ConflictAlias(input.alias)
      case None =>
        val newProject = BoundedContext.create(input.alias, input.name, input.overview)
        projectRepository.insert(newProject)
        CreateBoundedContextOutput.Success(newProject)
    }
  }
}

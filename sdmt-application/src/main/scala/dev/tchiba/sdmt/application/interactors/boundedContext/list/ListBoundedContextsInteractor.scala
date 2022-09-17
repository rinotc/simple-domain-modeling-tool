package dev.tchiba.sdmt.application.interactors.boundedContext.list

import dev.tchiba.arch.usecase.NoInput
import dev.tchiba.sdmt.core.boundedContext.BoundedContextRepository
import dev.tchiba.sdmt.usecase.boundedContext.list.{ListBoundedContextsOutput, ListBoundedContextsUseCase}

import javax.inject.Inject
import scala.annotation.unused

class ListBoundedContextsInteractor @Inject() (
    boundedContextRepository: BoundedContextRepository
) extends ListBoundedContextsUseCase {
  override def handle(@unused input: NoInput[ListBoundedContextsOutput]): ListBoundedContextsOutput = {
    val boundedContexts = boundedContextRepository.all
    ListBoundedContextsOutput(boundedContexts)
  }
}

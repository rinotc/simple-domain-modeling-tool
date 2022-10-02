package dev.tchiba.sdmt.usecase.boundedContext.list

import dev.tchiba.arch.usecase.Output
import dev.tchiba.sdmt.core.boundedContext.BoundedContext

final case class ListBoundedContextsOutput(boundedContexts: Seq[BoundedContext]) extends Output

package dev.tchiba.sdmt.infra

import dev.tchiba.sdmt.core.boundedContext._
import dev.tchiba.sdmt.infra.scalikejdbc.BoundedContexts

package object boundedContext {

  private[boundedContext] def translate(row: BoundedContexts): BoundedContext = {
    BoundedContext.reconstruct(
      id = BoundedContextId.fromString(row.boundedContextId),
      alias = BoundedContextAlias(row.boundedContextAlias),
      name = BoundedContextName(row.boundedContextName),
      overview = BoundedContextOverview(row.boundedContextOverview)
    )
  }
}

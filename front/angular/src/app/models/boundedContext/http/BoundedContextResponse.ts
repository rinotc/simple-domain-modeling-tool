import {BoundedContext} from "../bounded-context";
import {BoundedContextId} from "../id/bounded-context-id";
import {BoundedContextAlias} from "../alias/bounded-context-alias";
import {BoundedContextName} from "../name/bounded-context-name";
import {BoundedContextOverview} from "../overview/bounded-context-overview";


export type BoundedContextResponse = {
  readonly id: string
  readonly alias: string
  readonly name: string
  readonly overview: string
}

export const BoundedContextResponse = {
  convert(res: BoundedContextResponse): BoundedContext {
    return new BoundedContext(
      new BoundedContextId(res.id),
      new BoundedContextAlias(res.alias),
      new BoundedContextName(res.name),
      new BoundedContextOverview(res.overview)
    )
  }
}


import {BoundedContext} from "../bounded-context";
import {BoundedContextId} from "../id/bounded-context-id";
import {BoundedContextAlias} from "../alias/bounded-context-alias";
import {BoundedContextName} from "../name/bounded-context-name";
import {BoundedContextOverview} from "../overview/bounded-context-overview";


export type ProjectResponse = {
  readonly projectId: string
  readonly projectAlias: string
  readonly projectName: string
  readonly projectOverview: string
}

export const ProjectResponse = {
  convert(res: ProjectResponse): BoundedContext {
    return new BoundedContext(
      new BoundedContextId(res.projectId),
      new BoundedContextAlias(res.projectAlias),
      new BoundedContextName(res.projectName),
      new BoundedContextOverview(res.projectOverview)
    )
  }
}


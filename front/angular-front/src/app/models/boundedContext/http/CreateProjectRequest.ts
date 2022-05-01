import {BoundedContextAlias} from "../alias/bounded-context-alias";
import {BoundedContextName} from "../name/bounded-context-name";
import {BoundedContextOverview} from "../overview/bounded-context-overview";

export type CreateProjectRequest = {
  alias: string,
  name: string,
  overview: string
}
export const CreateProjectRequest = {
  translate(alias: BoundedContextAlias, name: BoundedContextName, overview: BoundedContextOverview): CreateProjectRequest {
    return {
      alias: alias.value,
      name: name.value,
      overview: overview.value
    };
  }
}

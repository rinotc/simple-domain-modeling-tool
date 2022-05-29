import {BoundedContext} from "../bounded-context";

export interface UpdateBoundedContextRequest {
  alias: string,
  name: string,
  overview: string
}
export const UpdateBoundedContextRequest = {
  translate(bc: BoundedContext): UpdateBoundedContextRequest {
    return {
      alias: bc.alias.value,
      name: bc.name.value,
      overview: bc.overview.value
    }
  }
}

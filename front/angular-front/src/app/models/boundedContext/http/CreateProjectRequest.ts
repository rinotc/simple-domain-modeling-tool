import {ProjectAlias} from "../alias/project-alias";
import {ProjectName} from "../name/project-name";
import {ProjectOverview} from "../overview/project-overview";

export type CreateProjectRequest = {
  alias: string,
  name: string,
  overview: string
}
export const CreateProjectRequest = {
  translate(alias: ProjectAlias, name: ProjectName, overview: ProjectOverview): CreateProjectRequest {
    return {
      alias: alias.value,
      name: name.value,
      overview: overview.value
    };
  }
}

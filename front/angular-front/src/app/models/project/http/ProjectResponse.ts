import {Project} from "../project";
import {ProjectId} from "../id/project-id";
import {ProjectAlias} from "../alias/project-alias";
import {ProjectName} from "../name/project-name";
import {ProjectOverview} from "../overview/project-overview";


export type ProjectResponse = {
  readonly projectId: string
  readonly projectAlias: string
  readonly projectName: string
  readonly projectOverview: string
}

export const ProjectResponse = {
  convert(res: ProjectResponse): Project {
    return new Project(
      new ProjectId(res.projectId),
      new ProjectAlias(res.projectAlias),
      new ProjectName(res.projectName),
      new ProjectOverview(res.projectOverview)
    )
  }
}


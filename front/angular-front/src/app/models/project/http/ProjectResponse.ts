import {Project} from "../project";
import {ProjectId} from "../id/project-id";
import {ProjectAlias} from "../alias/project-alias";
import {ProjectName} from "../name/project-name";
import {ProjectOverview} from "../overview/project-overview";

export class ProjectResponse {
  constructor(
    readonly projectId: string,
    readonly projectAlias: string,
    readonly projectName: string,
    readonly projectOverview: string
  ) {}

  get convert(): Project {
    return new Project(
      new ProjectId(this.projectId),
      new ProjectAlias(this.projectAlias),
      new ProjectName(this.projectName),
      new ProjectOverview(this.projectOverview)
    )
  }
}

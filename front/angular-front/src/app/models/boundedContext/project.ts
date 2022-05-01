import {ProjectId} from "./id/project-id";
import {ProjectAlias} from "./alias/project-alias";
import {ProjectName} from "./name/project-name";
import {ProjectOverview} from "./overview/project-overview";

export class Project {

  constructor(
    readonly id: ProjectId,
    readonly alias: ProjectAlias,
    readonly name: ProjectName,
    readonly overview: ProjectOverview
  ) {}
  
  equals(other: Project): boolean {
    return this.id.equals(other.id);
  }
}

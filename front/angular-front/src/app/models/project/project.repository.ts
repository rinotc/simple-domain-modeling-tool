import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {map, Observable} from "rxjs";
import {config} from "../../config";
import {Project} from "./project";
import {ApiCollectionResponse} from "../ApiCollectionResponse";

interface ProjectResponse {
  projectId: string,
  projectAlias: string,
  projectName: string,
  projectOverview: string
}

@Injectable({providedIn: 'root'})
export class ProjectRepository {

  constructor(private http: HttpClient) {}

  getProjects(): Observable<Project[]> {
    return this.http
        .get<ApiCollectionResponse<ProjectResponse>>(`${config.apiHost}/projects`)
        .pipe(
          map(res => res.data.map(p => ProjectRepository.toModel(p)))
        )

  }

  private static toModel(response: ProjectResponse): Project {
    return new Project(
      response.projectId,
      response.projectAlias,
      response.projectName,
      response.projectOverview
    )
  }
}

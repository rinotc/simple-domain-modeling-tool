import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {map, Observable} from "rxjs";
import {config} from "../../config";
import {Project} from "./project";
import {ApiCollectionResponse} from "../ApiCollectionResponse";
import {ProjectResponse} from "./http/ProjectResponse";
import {ProjectAlias} from "./alias/project-alias";


@Injectable({providedIn: 'root'})
export class ProjectRepository {

  constructor(private http: HttpClient) {}

  getProjects(): Observable<Project[]> {
    return this.http
        .get<ApiCollectionResponse<ProjectResponse>>(`${config.apiHost}/projects`)
        .pipe(
          map(res => res.data.map(p => p.convert))
        )
  }

  findBy(alias: ProjectAlias): Observable<Project> {
    return this.http
      .get<ProjectResponse>(`${config.apiHost}/projects/${alias.value}`)
      .pipe(
        map(res => res.convert)
      );
  }
}

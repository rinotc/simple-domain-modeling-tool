import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {catchError, map, Observable, of} from "rxjs";
import {config} from "../../config";
import {Project} from "./project";
import {ApiCollectionResponse} from "../ApiCollectionResponse";
import {ProjectResponse} from "./http/ProjectResponse";
import {ProjectAlias} from "./alias/project-alias";
import {ProjectName} from "./name/project-name";
import {ProjectOverview} from "./overview/project-overview";
import {CreateProjectRequest} from "./http/CreateProjectRequest";


@Injectable({providedIn: 'root'})
export class ProjectRepository {

  constructor(private http: HttpClient) {}

  getProjects(): Observable<Project[]> {
    return this.http
        .get<ApiCollectionResponse<ProjectResponse>>(`${config.apiHost}/projects`)
        .pipe(
          map(res => res.data.map(p => ProjectResponse.convert(p))),
          catchError(this.handleError('getProjects', []))
        )
  }

  findBy(alias: ProjectAlias): Observable<Project> {
    return this.http
      .get<ProjectResponse>(`${config.apiHost}/projects/${alias.value}`)
      .pipe(
        map(res => ProjectResponse.convert(res))
      );
  }

  create(alias: ProjectAlias, name: ProjectName, overview: ProjectOverview): Observable<CreateProjectRequest> {
    return this.http
      .post<CreateProjectRequest>(`${config.apiHost}/projects`, CreateProjectRequest.translate(alias, name, overview))
  }

  /**
   * 失敗したHttp操作を処理します。
   * アプリを持続させます。
   *
   * @param operation - 失敗した操作の名前
   * @param result - observableな結果として返す任意の値
   */
  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      // TODO: リモート上のロギング基盤にエラーを送信する
      console.error(error); // かわりにconsoleに出力

      // TODO: ユーザーへの開示のためにエラーの変換処理を改善する
      console.log(`${operation} failed: ${error.message}`);

      // 空の結果を返して、アプリを持続可能にする
      return of(result as T);
    };
  }
}

import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {catchError, map, Observable, of} from "rxjs";
import {config} from "../../config";
import {BoundedContext} from "./bounded-context";
import {ApiCollectionResponse} from "../ApiCollectionResponse";
import {BoundedContextResponse} from "./http/BoundedContextResponse";
import {BoundedContextAlias} from "./alias/bounded-context-alias";
import {BoundedContextName} from "./name/bounded-context-name";
import {BoundedContextOverview} from "./overview/bounded-context-overview";
import {CreateBoundedContextRequest} from "./http/CreateBoundedContextRequest";
import {BoundedContextId} from "./id/bounded-context-id";


@Injectable({providedIn: 'root'})
export class BoundedContextRepository {

  constructor(private http: HttpClient) {}

  getAll(): Observable<BoundedContext[]> {
    return this.http
        .get<ApiCollectionResponse<BoundedContextResponse>>(`${config.apiHost}/bounded-contexts`)
        .pipe(
          map(res => res.data.map(p => BoundedContextResponse.convert(p))),
          catchError(this.handleError('getAll', []))
        )
  }

  findById(id: BoundedContextId): Observable<BoundedContext> {
    return this.http
      .get<BoundedContextResponse>(`${config.apiHost}/bounded-contexts/${id.value}`)
      .pipe(
        map(res => BoundedContextResponse.convert(res))
      );
  }

  findByAlias(alias: BoundedContextAlias): Observable<BoundedContext> {
    return this.http
      .get<BoundedContextResponse>(`${config.apiHost}/bounded-contexts/${alias.value}`)
      .pipe(
        map(res => BoundedContextResponse.convert(res))
      );
  }

  create(alias: BoundedContextAlias, name: BoundedContextName, overview: BoundedContextOverview): Observable<CreateBoundedContextRequest> {
    return this.http
      .post<CreateBoundedContextRequest>(`${config.apiHost}/bounded-contexts`, CreateBoundedContextRequest.translate(alias, name, overview))
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

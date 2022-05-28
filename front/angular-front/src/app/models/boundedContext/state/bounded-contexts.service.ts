import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {BoundedContextsStore} from './bounded-contexts.store';
import {Observable, tap} from "rxjs";
import {ApiCollectionResponse} from "../../ApiCollectionResponse";
import {BoundedContextResponse} from "../http/BoundedContextResponse";
import {config} from "../../../config";
import {BoundedContextId} from "../id/bounded-context-id";
import {CreateBoundedContextRequest} from "../http/CreateBoundedContextRequest";
import {BoundedContextAlias} from "../alias/bounded-context-alias";
import {BoundedContextName} from "../name/bounded-context-name";
import {BoundedContextOverview} from "../overview/bounded-context-overview";
import {BoundedContext} from "../bounded-context";
import {UpdateBoundedContextRequest} from "../http/update-bounded-context-request";

@Injectable({ providedIn: 'root' })
export class BoundedContextsService {

  constructor(
    private boundedContextStore: BoundedContextsStore,
    private http: HttpClient
  ) {}

  fetchAll(): void {
    this.http
      .get<ApiCollectionResponse<BoundedContextResponse>>(`${config.apiHost}/bounded-contexts`)
      .subscribe(res => {
        const contexts = res.data.map(p => BoundedContextResponse.convert(p))
        this.boundedContextStore.update((state) => {
          return {
            contexts: state.contexts.replace(contexts)
          };
        });
      });
  }

  fetchById(id: BoundedContextId) {
    this.http
      .get<BoundedContextResponse>(`${config.apiHost}/bounded-contexts/${id.value}`)
      .pipe(
        tap(res => {
          const bc = BoundedContextResponse.convert(res)
          this.boundedContextStore.update((state) => {
            return {
              contexts: state.contexts.append(bc)
            };
          })
        })
      );
  }

  create(alias: BoundedContextAlias, name: BoundedContextName, overview: BoundedContextOverview): Observable<BoundedContextResponse> {
    return this.http
      .post<BoundedContextResponse>(`${config.apiHost}/bounded-contexts`, CreateBoundedContextRequest.translate(alias, name, overview))
      .pipe(
        tap(res => {
          const newBc = BoundedContextResponse.convert(res);
          this.boundedContextStore.update(state => {
            state.contexts.append(newBc);
          })
        })
      );
  }

  update(boundedContext: BoundedContext): Observable<BoundedContextResponse> {
    return this.http
      .put<BoundedContextResponse>(`${config.apiHost}/bounded-contexts/${boundedContext.id.value}`, UpdateBoundedContextRequest.translate(boundedContext))
      .pipe(
        tap(res => {
          const updatedBc = BoundedContextResponse.convert(res);
          this.boundedContextStore.update(state => {
            state.contexts.append(updatedBc);
          });
        })
      )
  }
}

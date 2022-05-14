import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {BoundedContextsStore} from './bounded-contexts.store';
import {tap} from "rxjs";
import {ApiCollectionResponse} from "../../models/ApiCollectionResponse";
import {BoundedContextResponse} from "../../models/boundedContext/http/BoundedContextResponse";
import {config} from "../../config";
import {BoundedContextId} from "../../models/boundedContext/id/bounded-context-id";
import {CreateBoundedContextRequest} from "../../models/boundedContext/http/CreateBoundedContextRequest";
import {BoundedContextAlias} from "../../models/boundedContext/alias/bounded-context-alias";
import {BoundedContextName} from "../../models/boundedContext/name/bounded-context-name";
import {BoundedContextOverview} from "../../models/boundedContext/overview/bounded-context-overview";

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

  create(alias: BoundedContextAlias, name: BoundedContextName, overview: BoundedContextOverview) {
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

}

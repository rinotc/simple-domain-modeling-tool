import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BoundedContextsStore } from './bounded-contexts.store';
import { lastValueFrom, Observable, tap } from 'rxjs';
import { ApiCollectionResponse } from '../../ApiCollectionResponse';
import { BoundedContextResponse } from '../http/BoundedContextResponse';
import { config } from '../../../config';
import { BoundedContextId } from '../id/bounded-context-id';
import { CreateBoundedContextRequest } from '../http/CreateBoundedContextRequest';
import { BoundedContextAlias } from '../alias/bounded-context-alias';
import { BoundedContextName } from '../name/bounded-context-name';
import { BoundedContextOverview } from '../overview/bounded-context-overview';
import { BoundedContext } from '../bounded-context';
import { UpdateBoundedContextRequest } from '../http/update-bounded-context-request';

@Injectable({ providedIn: 'root' })
export class BoundedContextsService {
  constructor(
    private boundedContextStore: BoundedContextsStore,
    private http: HttpClient
  ) {}

  fetchAll(): void {
    this.http
      .get<ApiCollectionResponse<BoundedContextResponse>>(
        `${config.apiHost}/bounded-contexts`
      )
      .subscribe((res) => {
        const contexts = res.data.map((p) => BoundedContextResponse.convert(p));
        this.boundedContextStore.update((state) => {
          return {
            contexts: state.contexts.replace(contexts),
          };
        });
      });
  }

  fetchByIdOrAlias(
    idOrAlias: BoundedContextId | BoundedContextAlias
  ): Promise<BoundedContextResponse> {
    const observable = this.http
      .get<BoundedContextResponse>(
        `${config.apiHost}/bounded-contexts/${idOrAlias.value}`
      )
      .pipe(
        tap((res) => {
          const bc = BoundedContextResponse.convert(res);
          this.boundedContextStore.update((state) => ({
            contexts: state.contexts.append(bc),
          }));
        })
      );
    return lastValueFrom(observable);
  }

  create(
    alias: BoundedContextAlias,
    name: BoundedContextName,
    overview: BoundedContextOverview
  ): Observable<BoundedContextResponse> {
    return this.http
      .post<BoundedContextResponse>(
        `${config.apiHost}/bounded-contexts`,
        CreateBoundedContextRequest.translate(alias, name, overview)
      )
      .pipe(
        tap((res) => {
          const newBc = BoundedContextResponse.convert(res);
          this.boundedContextStore.update((state) => ({
            contexts: state.contexts.append(newBc),
          }));
        })
      );
  }

  update(boundedContext: BoundedContext): Promise<BoundedContextResponse> {
    const response$ = this.http
      .put<BoundedContextResponse>(
        `${config.apiHost}/bounded-contexts/${boundedContext.id.value}`,
        UpdateBoundedContextRequest.translate(boundedContext)
      )
      .pipe(
        tap((res) => {
          const updatedBc = BoundedContextResponse.convert(res);
          this.boundedContextStore.update((state) => ({
            contexts: state.contexts.append(updatedBc),
          }));
        })
      );

    return lastValueFrom(response$);
  }

  delete(id: BoundedContextId) {
    const response$ = this.http.delete(
      `${config.apiHost}/bounded-contexts/${id.value}`
    );
    return lastValueFrom(response$);
  }
}

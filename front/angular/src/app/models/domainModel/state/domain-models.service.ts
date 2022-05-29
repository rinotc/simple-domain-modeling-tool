import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { DomainModelsStore } from './domain-models.store';
import { ApiCollectionResponse } from '../../ApiCollectionResponse';
import { DomainModelResponse } from '../http/domain-model-response';
import { config } from '../../../config';
import { BoundedContextId } from '../../boundedContext/id/bounded-context-id';
import { CreateDomainModelInput } from './io/create-domain-model-input';
import { CreateDomainModelRequest } from '../http/create-domain-model-request';
import { Observable, tap } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class DomainModelsService {
  constructor(
    private domainModelsStore: DomainModelsStore,
    private http: HttpClient
  ) {}

  fetchAll(boundedContextId: BoundedContextId): void {
    this.http
      .get<ApiCollectionResponse<DomainModelResponse>>(
        `${config.apiHost}/bounded-contexts/${boundedContextId.value}/domain-models`
      )
      .subscribe((res) => {
        const dms = res.data.map((r) => DomainModelResponse.translate(r));
        this.domainModelsStore.update((state) => {
          return {
            models: state.models.replace(dms),
          };
        });
      });
  }

  create(input: CreateDomainModelInput): Observable<DomainModelResponse> {
    return this.http
      .post<DomainModelResponse>(
        `${config.apiHost}/bounded-contexts/${input.boundedContextId.value}/domain-models`,
        CreateDomainModelRequest.from(input)
      )
      .pipe(
        tap((res) => {
          const newDM = DomainModelResponse.translate(res);
          this.domainModelsStore.update((state) => {
            return {
              models: state.models.append(newDM),
            };
          });
        })
      );
  }
}

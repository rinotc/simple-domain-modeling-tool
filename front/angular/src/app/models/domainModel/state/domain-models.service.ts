import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { DomainModelsStore } from './domain-models.store';
import { ApiCollectionResponse } from '../../ApiCollectionResponse';
import { DomainModelResponse } from '../http/domain-model-response';
import { config } from '../../../config';
import { BoundedContextId } from '../../boundedContext/id/bounded-context-id';
import { CreateDomainModelInput } from './io/create-domain-model-input';
import { CreateDomainModelRequest } from '../http/create-domain-model-request';
import { lastValueFrom, Observable, tap } from 'rxjs';
import { DomainModelId } from '../id/domain-model-id';
import { EnglishName } from '../englishName/english-name';
import { BoundedContextAlias } from '../../boundedContext/alias/bounded-context-alias';
import { DomainModel } from '../domain-model';
import { DomainModelHttpIO } from './http/io/domain-model-http-io';

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
          console.log(state);
          console.log(state.models.replace(dms));
          return {
            models: state.models.replace(dms),
          };
        });
      });
  }

  fetchByIdOrEnglishName(
    idOrEnglishName: DomainModelId | EnglishName,
    idOrAlias: BoundedContextId | BoundedContextAlias
  ): Promise<DomainModelResponse> {
    const response$ = this.http
      .get<DomainModelResponse>(
        `${config.apiHost}/bounded-contexts/${idOrAlias.value}/domain-models/${idOrEnglishName.value}`
      )
      .pipe(
        tap((res) => {
          const dm = DomainModelResponse.translate(res);
          this.domainModelsStore.update((state) => {
            console.log(state);
            console.log(state.models.append(dm));
            return {
              models: state.models.append(dm),
            };
          });
        })
      );

    return lastValueFrom(response$);
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

  update(domainModel: DomainModel): Observable<DomainModelHttpIO> {
    return this.http
      .put<DomainModelHttpIO>(
        `${config.apiHost}/bounded-contexts/${domainModel.boundedContextId.value}/domain-models/${domainModel.id.value}`,
        DomainModelHttpIO.update(domainModel)
      )
      .pipe(
        tap((res) => {
          const updatedDM = DomainModelHttpIO.convert(res);
          this.domainModelsStore.update((state) => {
            return {
              models: state.models.append(updatedDM),
            };
          });
        })
      );
  }
}

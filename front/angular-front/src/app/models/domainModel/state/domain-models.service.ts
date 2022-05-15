import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {DomainModelsStore} from './domain-models.store';
import {ApiCollectionResponse} from "../../ApiCollectionResponse";
import {DomainModelResponse} from "../http/DomainModelResponse";
import {config} from "../../../config";
import {BoundedContextId} from "../../boundedContext/id/bounded-context-id";

@Injectable({ providedIn: 'root' })
export class DomainModelsService {

  constructor(private domainModelsStore: DomainModelsStore, private http: HttpClient) {}

  fetchAll(boundedContextId: BoundedContextId): void {
    this.http
      .get<ApiCollectionResponse<DomainModelResponse>>(`${config.apiHost}/bounded-contexts/${boundedContextId.value}`)
      .subscribe(res => {
        const dms = res.data.map(r => DomainModelResponse.translate(r));
        this.domainModelsStore.update((state) => {
          return {
            models: state.models.replace(dms)
          };
        });
      });
  }
}

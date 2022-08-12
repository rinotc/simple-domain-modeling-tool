import { Injectable } from '@angular/core';
import { Query } from '@datorama/akita';
import { DomainModelsState, DomainModelsStore } from './domain-models.store';
import { firstValueFrom, lastValueFrom, Observable } from 'rxjs';
import { DomainModels } from '../collection/domain-models';

@Injectable({ providedIn: 'root' })
export class DomainModelsQuery extends Query<DomainModelsState> {
  models$: Observable<DomainModels> = this.select('models');

  get models(): Promise<DomainModels> {
    return firstValueFrom(this.models$);
  }

  constructor(protected override store: DomainModelsStore) {
    super(store);
  }
}

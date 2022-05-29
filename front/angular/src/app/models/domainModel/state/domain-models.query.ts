import { Injectable } from '@angular/core';
import { Query } from '@datorama/akita';
import { DomainModelsState, DomainModelsStore } from './domain-models.store';
import { Observable } from 'rxjs';
import { DomainModels } from '../domain-models';

@Injectable({ providedIn: 'root' })
export class DomainModelsQuery extends Query<DomainModelsState> {
  models$: Observable<DomainModels> = this.select('models');

  constructor(protected override store: DomainModelsStore) {
    super(store);
  }
}

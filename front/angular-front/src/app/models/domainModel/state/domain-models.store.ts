import {Injectable} from '@angular/core';
import {Store, StoreConfig} from '@datorama/akita';
import {DomainModels} from "../domain-models";

export interface DomainModelsState {
  models: DomainModels
}

const DomainModelsState = {
  createInitialState(): DomainModelsState {
    return {
      models: DomainModels.empty()
    };
  }
}

@Injectable({ providedIn: 'root' })
@StoreConfig({ name: 'DomainModels' })
export class DomainModelsStore extends Store<DomainModelsState> {

  constructor() {
    super(DomainModelsState.createInitialState());
  }

}

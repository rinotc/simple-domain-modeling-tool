import {Injectable} from '@angular/core';
import {Store, StoreConfig} from '@datorama/akita';
import {BoundedContexts} from "../../models/boundedContext/bounded-contexts";

export interface BoundedContextsState {
  contexts: BoundedContexts
}

export function createInitialState(): BoundedContextsState {
  return {
    contexts: BoundedContexts.empty()
  };
}

@Injectable({ providedIn: 'root' })
@StoreConfig({ name: 'boundedContext' })
export class BoundedContextsStore extends Store<BoundedContextsState> {

  constructor() {
    super(createInitialState());
  }
}

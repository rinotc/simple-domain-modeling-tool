import { Injectable } from '@angular/core';
import { Query } from '@datorama/akita';
import {
  BoundedContextsState,
  BoundedContextsStore,
} from './bounded-contexts.store';
import { firstValueFrom, Observable } from 'rxjs';
import { BoundedContexts } from '../bounded-contexts';

@Injectable({ providedIn: 'root' })
export class BoundedContextsQuery extends Query<BoundedContextsState> {
  contexts$: Observable<BoundedContexts> = this.select('contexts');

  get contexts(): Promise<BoundedContexts> {
    return firstValueFrom(this.contexts$);
  }

  constructor(protected override store: BoundedContextsStore) {
    super(store);
  }
}

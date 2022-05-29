import {Injectable} from '@angular/core';
import {Query} from '@datorama/akita';
import {HeaderState, HeaderStore} from './header.store';

@Injectable({ providedIn: 'root' })
export class HeaderQuery extends Query<HeaderState> {

  constructor(protected override store: HeaderStore) {
    super(store);
  }

  get nowContext(): string {
    return this.getValue().context;
  }
}

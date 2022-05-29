import { Injectable } from '@angular/core';
import { Store, StoreConfig } from '@datorama/akita';

export type HeaderState = {
  context: string;
};
const HeaderState = {
  createInitialState(): HeaderState {
    return {
      context: '',
    };
  },
};

@Injectable({ providedIn: 'root' })
@StoreConfig({ name: 'header' })
export class HeaderStore extends Store<HeaderState> {
  constructor() {
    super(HeaderState.createInitialState());
  }
}

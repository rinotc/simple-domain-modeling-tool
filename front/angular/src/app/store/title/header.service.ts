import { Injectable } from '@angular/core';
import { HeaderStore } from './header.store';

@Injectable({ providedIn: 'root' })
export class HeaderService {
  constructor(private titleStore: HeaderStore) {}

  update(title: string) {
    this.titleStore.update({ context: title });
  }
}

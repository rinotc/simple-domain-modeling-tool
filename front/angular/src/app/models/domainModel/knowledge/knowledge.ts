import { PreferNominal } from '../../prefer-nominal';

export class Knowledge {
  // noinspection JSUnusedGlobalSymbols
  readonly _knowledgeBrand: PreferNominal;

  constructor(readonly value: string) {}

  equals(other: Knowledge): boolean {
    return this.value === other.value;
  }
}

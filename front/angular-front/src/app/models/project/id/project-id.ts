import {PreferNominal} from "../../prefer-nominal";

// noinspection JSUnusedLocalSymbols,JSUnusedGlobalSymbols
export class ProjectId {
  readonly _projectIdBrand: PreferNominal;
  readonly value: string

  constructor(value: string) {
    const require = value.length === 36;
    if (!require) {
      throw new TypeError('project id value length must be 36 length.')
    }
    this.value = value;
  }
}

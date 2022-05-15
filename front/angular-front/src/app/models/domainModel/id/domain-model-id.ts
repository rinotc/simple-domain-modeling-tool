import {PreferNominal} from "../../prefer-nominal";
import * as E from "fp-ts/Either";

export class DomainModelId {
  // noinspection JSUnusedGlobalSymbols
  readonly _domainModelIdBrand: PreferNominal;

  constructor(readonly value: string) {
    if (!DomainModelId.mustValueLengthEqual36(value)) {
      throw new TypeError(DomainModelId.requirementErrorMessage(value));
    }
  }

  equals(other: DomainModelId): boolean {
    return this.value === other.value;
  }

  static validate(value: string): E.Either<string, DomainModelId> {
    if (this.mustValueLengthEqual36(value)) {
      const id = new DomainModelId(value)
      return E.right(id)
    }
    return E.left(this.requirementErrorMessage(value));
  }

  private static requirementErrorMessage(value: string): string {
    return `DomainModelId value length must be 36 length, but ${value.length}. value is ${value}`;
  }

  private static mustValueLengthEqual36(value: string): boolean {
    return value.length === 36;
  }
}

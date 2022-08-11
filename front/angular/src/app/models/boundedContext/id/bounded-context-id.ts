import { PreferNominal } from '../../prefer-nominal';
import * as E from 'fp-ts/Either';

export class BoundedContextId {
  // noinspection JSUnusedGlobalSymbols
  readonly _boundedContextIdBrand: PreferNominal;

  constructor(readonly value: string) {
    if (!BoundedContextId.mustValueLengthEqual36(value)) {
      throw new TypeError(BoundedContextId.requirementErrorMessage(value));
    }
  }

  equals(other: BoundedContextId): boolean {
    return this.value === other.value;
  }

  static isValid(value: string): boolean {
    return this.mustValueLengthEqual36(value);
  }

  static validate(value: string): E.Either<string, BoundedContextId> {
    if (this.mustValueLengthEqual36(value)) {
      const id = new BoundedContextId(value);
      return E.right(id);
    }
    return E.left(this.requirementErrorMessage(value));
  }

  private static requirementErrorMessage(value: string): string {
    return `BoundedContextId value length must be 36 length, but ${value.length}. value is ${value}`;
  }

  private static mustValueLengthEqual36(value: string): boolean {
    return value.length === 36;
  }
}

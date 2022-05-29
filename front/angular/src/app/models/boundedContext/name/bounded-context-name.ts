import { PreferNominal } from '../../prefer-nominal';
import * as E from 'fp-ts/Either';

export class BoundedContextName {
  // noinspection JSUnusedGlobalSymbols
  readonly _boundedContextNameBrand: PreferNominal;

  constructor(readonly value: string) {
    if (!BoundedContextName.isValid(value)) {
      throw new TypeError(this.requirementErrorMessage);
    }
  }

  private get requirementErrorMessage(): string {
    return `BoundedContextName must to be 1 to 100 characters. but value length is ${this.value.length}`;
  }

  equals(other: BoundedContextName): boolean {
    return this.value === other.value;
  }

  static validate(value: string): E.Either<string, BoundedContextName> {
    if (this.isValid(value)) {
      return E.right(new BoundedContextName(value));
    }
    return E.left(this.validationErrorMessage);
  }

  static isValid(value: string): boolean {
    return this.mustBe1To100Characters(value);
  }

  static validationErrorMessage: string =
    '境界づけられたコンテキストの名称は1文字以上100文字以下である必要があります';

  private static mustBe1To100Characters(value: string): boolean {
    return value.length >= 1 && value.length <= 100;
  }
}

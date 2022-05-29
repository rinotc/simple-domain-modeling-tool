import { PreferNominal } from '../../prefer-nominal';
import * as E from 'fp-ts/Either';

export class UbiquitousName {
  // noinspection JSUnusedGlobalSymbols
  readonly _ubiquitousNameBrand: PreferNominal;

  constructor(readonly value: string) {
    if (!UbiquitousName.isValid(value)) {
      throw new TypeError(this.requirementErrorMessage);
    }
  }

  private get requirementErrorMessage(): string {
    return `UbiquitousName must to be 1 to 50 characters. but value length is ${this.value.length}`;
  }

  equals(other: UbiquitousName): boolean {
    return this.value === other.value;
  }

  static validate(value: string): E.Either<string, UbiquitousName> {
    if (this.isValid(value)) {
      return E.right(new UbiquitousName(value));
    }
    return E.left(this.validationErrorMessage);
  }

  static isValid(value: string): boolean {
    return this.mustBe1To50Characters(value);
  }

  static validationErrorMessage: string =
    'ユビキタス名は1文字以上50文字以下である必要があります';

  private static mustBe1To50Characters(value: string): boolean {
    return value.length >= 1 && value.length <= 50;
  }
}

import {PreferNominal} from "../../prefer-nominal";
import * as E from "fp-ts/Either";

export class EnglishName {
  // noinspection JSUnusedGlobalSymbols
  readonly _englishNameBrand: PreferNominal;

  constructor(readonly value: string) {
    if (!EnglishName.isValid(value)) {
      throw new TypeError(this.requirementErrorMessage);
    }
  }

  private get requirementErrorMessage(): string {
    return `EnglishName must between 1 to 100 length and only use alphabet or numerical, but value is "${this.value}"`;
  }

  equals(other: EnglishName): boolean {
    return this.value === other.value;
  }

  static validate(value: string): E.Either<string, EnglishName> {
    if (this.isValid(value)) {
      return E.right(new EnglishName(value));
    }
    return E.left(this.validationErrorMessage);
  }

  static isValid(value: string): boolean {

    console.log(this.mustBe1To100Characters(value));
    console.log(this.mustOnlyAlphanumerical(value));

    return this.mustBe1To100Characters(value) && this.mustOnlyAlphanumerical(value);
  }

  static validationErrorMessage: string = '英語名は1文字以上100文字以下である必要があります';

  private static mustBe1To100Characters(value: string): boolean {
    return value.length >= 1 && value.length <= 100;
  }

  private static mustOnlyAlphanumerical(value: string): boolean {
    const regex = new RegExp('^[a-zA-Z\\d]{1,100}$');
    return regex.test(value);
  }
}

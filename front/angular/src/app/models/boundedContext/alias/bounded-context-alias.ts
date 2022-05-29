import { PreferNominal } from '../../prefer-nominal';
import * as E from 'fp-ts/Either';

export class BoundedContextAlias {
  // noinspection JSUnusedGlobalSymbols
  readonly _boundedContextAliasBrand: PreferNominal;

  constructor(readonly value: string) {
    if (!BoundedContextAlias.isValid(value)) {
      throw new TypeError(this.requirementErrorMessage);
    }
  }

  private get requirementErrorMessage(): string {
    return `BoundedContextAlias value must be 1 to 32 characters and alphanumerical, but value is ${this.value}`;
  }

  equals(other: BoundedContextAlias): boolean {
    return this.value === other.value;
  }

  static validate(value: string): E.Either<string, BoundedContextAlias> {
    if (this.isValid(value)) {
      return E.right(new BoundedContextAlias(value));
    }
    return E.left(this.validationErrorMessage);
  }

  static isValid(value: string): boolean {
    return (
      this.mustNonEmpty(value) &&
      this.mustLessThan32Length(value) &&
      this.mustOnlyAlphanumerical(value)
    );
  }

  static validationErrorMessage: string =
    '境界づけられたコンテキストのエイリアスは1文字以上32文字以下で、英数のみ利用可能です';

  private static mustNonEmpty(value: string): boolean {
    return value.length > 0;
  }

  private static mustLessThan32Length(value: string): boolean {
    return value.length <= 32;
  }

  private static mustOnlyAlphanumerical(value: string): boolean {
    const regex = new RegExp('^[0-9a-zA-Z]{1,32}$');
    return regex.test(value);
  }
}

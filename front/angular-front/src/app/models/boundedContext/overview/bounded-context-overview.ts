import {PreferNominal} from "../../prefer-nominal";
import * as E from 'fp-ts/Either';

export class BoundedContextOverview {
  // noinspection JSUnusedGlobalSymbols
  readonly _boundedContextOverviewBrand: PreferNominal;

  constructor(readonly value: string) {
    if (!BoundedContextOverview.isValid(value)) {
      throw new TypeError(this.requirementErrorMessage);
    }
  }

  private get requirementErrorMessage(): string {
    return `BoundedContextOverview value must less than 500, but length is ${this.value.length}`;
  }

  equals(other: BoundedContextOverview): boolean {
    return this.value === other.value
  }

  static validate(value: string): E.Either<string, BoundedContextOverview> {
    if (this.isValid(value)) {
      return E.right(new BoundedContextOverview(value));
    }
    return E.left(this.validationErrorMessage);
  }

  static isValid(value: string): boolean {
    return this.mustLessThan500(value);
  }

  static validationErrorMessage: string = '境界づけられたコンテキストの概要は500文字以下である必要があります';

  private static mustLessThan500(value: string): boolean {
    return value.length <= 500;
  }
}

import {PreferNominal} from "../../prefer-nominal";
import * as E from "fp-ts/Either";

export class ProjectName {
  // noinspection JSUnusedGlobalSymbols
  readonly _projectNameBrand: PreferNominal;

  constructor(readonly value: string) {
    if (!ProjectName.isValid(value)) {
      throw new TypeError(this.requirementErrorMessage);
    }
  }

  private get requirementErrorMessage(): string {
    return `ProjectName must to be 1 to 100 characters. but value length is ${this.value.length}`
  }

  equals(other: ProjectName): boolean {
    return this.value === other.value;
  }

  static validate(value: string): E.Either<string, ProjectName> {
    if (this.isValid(value)) {
      return E.right(new ProjectName(value));
    }
    return E.left(this.validationErrorMessage);
  }

  static isValid(value: string): boolean {
    return this.mustBe1To100Characters(value);
  }

  static validationErrorMessage: string = 'プロジェクト名は1文字以上100文字以下である必要があります';

  private static mustBe1To100Characters(value: string): boolean {
    return value.length >= 1 && value.length <= 100;
  }
}

import {PreferNominal} from "../../prefer-nominal";
import * as E from 'fp-ts/Either';

export class ProjectOverview {
  // noinspection JSUnusedGlobalSymbols
  readonly _projectNameBrand: PreferNominal;

  constructor(readonly value: string) {
    if (!ProjectOverview.mustLessThan500(value)) {
      throw new TypeError(this.requirementErrorMessage);
    }
  }

  private get requirementErrorMessage(): string {
    return `ProjectOverview value must less than 500, but length is ${this.value.length}`;
  }

  equals(other: ProjectOverview): boolean {
    return this.value === other.value
  }

  static validate(value: string): E.Either<string, ProjectOverview> {
    if (this.isValid(value)) {
      return E.right(new ProjectOverview(value));
    }
    return E.left(this.validationErrorMessage);
  }

  static isValid(value: string): boolean {
    return this.mustLessThan500(value);
  }

  static validationErrorMessage: string = 'プロジェクト概要は500文字以下である必要があります';

  private static mustLessThan500(value: string): boolean {
    return value.length <= 500;
  }
}

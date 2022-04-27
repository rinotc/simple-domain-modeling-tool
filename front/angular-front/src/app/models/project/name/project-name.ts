import {PreferNominal} from "../../prefer-nominal";
import * as E from "fp-ts/Either";

export class ProjectName {
  // noinspection JSUnusedGlobalSymbols
  readonly _projectNameBrand: PreferNominal;
  readonly value: string;

  constructor(value: string) {
    if (!ProjectName.mustLessThan100(value)) {
      throw new TypeError(ProjectName.projectNameRequirementErrorMessage);
    }
    this.value = value;
  }

  equals(other: ProjectName): boolean {
    return this.value === other.value;
  }

  static validate(value: string): E.Either<string, ProjectName> {
    if (this.mustLessThan100(value)) {
      return E.right(new ProjectName(value));
    }
    return E.left(this.projectNameRequirementErrorMessage);
  }

  private static projectNameRequirementErrorMessage: string = 'project name value must less than 100.';

  private static mustLessThan100(value: string): boolean {
    return value.length <= 100;
  }
}

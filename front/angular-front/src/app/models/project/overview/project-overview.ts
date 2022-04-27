import {PreferNominal} from "../../prefer-nominal";
import * as E from 'fp-ts/Either';

export class ProjectOverview {
  // noinspection JSUnusedGlobalSymbols
  readonly _projectNameBrand: PreferNominal;

  constructor(readonly value: string) {
    if (!ProjectOverview.mustLessThan500(value)) {
      throw new TypeError(ProjectOverview.projectNameRequirementErrorMessage);
    }
  }

  equals(other: ProjectOverview): boolean {
    return this.value === other.value
  }

  static validate(value: string): E.Either<string, ProjectOverview> {
    if (this.mustLessThan500(value)) {
      return E.right(new ProjectOverview(value));
    }
    return E.left(this.projectNameRequirementErrorMessage);
  }

  private static projectNameRequirementErrorMessage: string = 'project overview must less than 500.';

  private static mustLessThan500(value: string): boolean {
    return value.length <= 500;
  }
}

import {PreferNominal} from "../../prefer-nominal";
import * as E from "fp-ts/Either";

export class ProjectId {
  // noinspection JSUnusedGlobalSymbols
  readonly _projectIdBrand: PreferNominal;

  constructor(readonly value: string) {
    if (!ProjectId.mustValueLengthEqual36(value)) {
      throw new TypeError(ProjectId.requirementErrorMessage(value));
    }
  }

  equals(other: ProjectId): boolean {
    return this.value === other.value;
  }

  static validate(value: string): E.Either<string, ProjectId> {
    if (this.mustValueLengthEqual36(value)) {
      const projectId = new ProjectId(value)
      return E.right(projectId)
    }
    return E.left(this.requirementErrorMessage(value));
  }

  private static requirementErrorMessage(value: string): string {
    return `project id value length must be 36 length, but ${value.length}. value is ${value}`;
  }

  private static mustValueLengthEqual36(value: string): boolean {
    return value.length === 36;
  }
}

import {PreferNominal} from "../../prefer-nominal";
import * as E from "fp-ts/Either";

// noinspection JSUnusedLocalSymbols,JSUnusedGlobalSymbols
export class ProjectId {
  readonly _projectIdBrand: PreferNominal;
  readonly value: string

  constructor(value: string) {
    if (!ProjectId.mustValueLengthEqual36(value)) {
      throw new TypeError(ProjectId.requirementErrorMessage);
    }
    this.value = value;
  }

  static validate(value: string): E.Either<string, ProjectId> {
    if (this.mustValueLengthEqual36(value)) {
      const projectId = new ProjectId(value)
      return E.right(projectId)
    }
    return E.left(this.requirementErrorMessage);
  }

  private static requirementErrorMessage: string = 'project id value length must be 36 length.';

  private static mustValueLengthEqual36(value: string): boolean {
    return value.length === 36;
  }
}

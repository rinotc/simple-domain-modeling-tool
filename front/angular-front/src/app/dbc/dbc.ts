class RequirementError implements Error {

  readonly name = 'requirement error'

  constructor(
    readonly message: string,
    readonly stack?: string
  ) {}
}

export function requirement(condition: boolean, message: any): void {
  if (!condition) {
    throw new RequirementError(message);
  }
}

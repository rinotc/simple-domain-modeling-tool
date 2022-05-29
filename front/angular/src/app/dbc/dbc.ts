export class RequirementError implements Error {

  readonly name = 'requirement error'

  constructor(
    readonly message: string,
    readonly stack?: string
  ) {}
}

export function requirement(condition: boolean, message?: any): void {
  if (!condition) {
    throw new RequirementError(message);
  }
}

export function notNull<T>(value: T | null | undefined, message?: any): void {
  if (!value) {
    throw new RequirementError(`not null error: ${message}`)
  }
}

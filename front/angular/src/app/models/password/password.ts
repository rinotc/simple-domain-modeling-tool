import { PreferNominal } from '../prefer-nominal';
import { requirement, RequirementError } from '../../dbc/dbc';
import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';
import { isString } from '../../dbc/user-defined-type-guards';

export class Password {
  // noinspection JSUnusedGlobalSymbols
  readonly _passwordBrand: PreferNominal;

  constructor(readonly value: string) {
    requirement(
      Password.isValid(value),
      'does not meet the password requirements.'
    );
  }

  static isValid(value: string): boolean {
    return (
      this.mustBetweenMinToMaxLengthCharacters(value) &&
      this.passwordRegex.test(value)
    );
  }

  private static passwordRegex = new RegExp(
    '^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!-/:-@\\[-`{-~])[!-~]{8,50}$'
  );

  private static mustBetweenMinToMaxLengthCharacters(value: string): boolean {
    return value.length >= this.MIN_LENGTH && value.length <= this.MAX_LENGTH;
  }

  static MIN_LENGTH = 8;
  static MAX_LENGTH = 50;

  static validator: ValidatorFn = (
    control: AbstractControl
  ): ValidationErrors | null => {
    const value: any = control.value;
    if (isString(value)) {
      return Password.isValid(value) ? null : { invalidPassword: value };
    }
    throw new RequirementError('invalid value type.');
  };
}

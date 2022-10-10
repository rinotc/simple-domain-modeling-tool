import { PreferNominal } from '../prefer-nominal';
import { requirement } from '../../dbc/dbc';

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
      this.mustBetween8to50LengthCharacters(value) &&
      this.passwordRegex.test(value)
    );
  }

  private static passwordRegex = new RegExp(
    '^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,50}$'
  );

  private static mustBetween8to50LengthCharacters(value: string): boolean {
    return value.length >= 8 && value.length <= 50;
  }
}

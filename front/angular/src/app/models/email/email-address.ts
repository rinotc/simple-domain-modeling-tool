import { PreferNominal } from '../prefer-nominal';
import { requirement } from '../../dbc/dbc';

export class EmailAddress {
  // noinspection JSUnusedGlobalSymbols
  readonly _passwordBrand: PreferNominal;

  constructor(readonly value: string) {
    requirement(
      EmailAddress.isValid(value),
      `${value} is not match email regex.`
    );
  }

  static isValid(value: string): boolean {
    return this.emailRegex.test(value);
  }

  private static emailRegex: RegExp = new RegExp(
    '^[a-zA-Z0-9_+-]+(.[a-zA-Z0-9_+-]+)*@([a-zA-Z0-9][a-zA-Z0-9-]*[a-zA-Z0-9]*\\.)+[a-zA-Z]{2,}$'
  );
}

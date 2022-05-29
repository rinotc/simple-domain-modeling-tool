import { BoundedContextAlias } from './bounded-context-alias';
import { expect } from '@angular/flex-layout/_private-utils/testing';
import { BoundedContextId } from '../id/bounded-context-id';
import * as E from 'fp-ts/Either';

describe('BoundedContextAlias', () => {
  describe('requirement', () => {
    it('should throw error when value length is 0', () => {
      const value = '';
      expect(value.length).toBe(0);
      expect(() => new BoundedContextId(value)).toThrow();
    });

    it('should throw error when value length is 33', () => {
      const value = 'A'.repeat(33);
      expect(value.length).toBe(33);
      expect(() => new BoundedContextAlias(value)).toThrow();
    });

    it('should throw requirement error when value has non alphanumerical characters', () => {
      const kana = 'アイウエオ';
      const hiragana = 'あいうえお';
      const containSymbol = 'abcde-fghij';
      expect(() => new BoundedContextAlias(kana)).toThrow();
      expect(() => new BoundedContextAlias(hiragana)).toThrow();
      expect(() => new BoundedContextAlias(containSymbol)).toThrow();
    });

    it('should create instance when value length is 32 and using alphanumerical characters', () => {
      const value = 'abcdeABCDEvwxyzVWXYZ1234567890gh';
      expect(value.length).toBe(32);
      expect(new BoundedContextAlias(value)).toBeTruthy();
    });
  });

  describe('validate', () => {
    it('should return left when value is empty', () => {
      const value = '';
      expect(value.length).toBe(0);
      const actual = BoundedContextAlias.validate(value);
      expect(E.isLeft(actual)).toBeTrue();
    });

    it('should return left when value length is 33', () => {
      const value = 'A'.repeat(33);
      expect(value.length).toBe(33);
      const actual = BoundedContextAlias.validate(value);
      expect(E.isLeft(actual)).toBeTrue();
    });

    it('should return left when value has non alphanumerical characters', () => {
      const kana = 'アイウエオ';
      const hiragana = 'あいうえお';
      const containSymbol = 'abcde-fghij';
      expect(E.isLeft(BoundedContextAlias.validate(kana))).toBeTrue();
      expect(E.isLeft(BoundedContextAlias.validate(hiragana))).toBeTrue();
      expect(E.isLeft(BoundedContextAlias.validate(containSymbol))).toBeTrue();
    });

    it('should return right when value length is 32 and alphanumerical characters.', () => {
      const value = 'abcdeABCDEvwxyzVWXYZ1234567890gh';
      expect(value.length).toBe(32);
      const actual = BoundedContextAlias.validate(value);
      expect(actual).toEqual(E.right(new BoundedContextAlias(value)));
    });
  });

  describe('equals', () => {
    it('should return true when compare same underlying value instances', () => {
      const a = new BoundedContextAlias('ABC');
      const b = new BoundedContextAlias('ABC');
      expect(a === b).toBeFalse(); // 異なるインスタンスである
      expect(a.equals(b)).toBeTrue(); // 値が等しければ同値とみなす
    });

    it('should return false when compare not same underlying value instances', () => {
      const a = new BoundedContextAlias('ALIAS');
      const b = new BoundedContextAlias('alias');
      expect(a === b).toBeFalse();
      expect(a.equals(b)).toBeFalse();
    });
  });
});

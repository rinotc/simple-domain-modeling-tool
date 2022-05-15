import {expect} from "@angular/flex-layout/_private-utils/testing";
import * as E from 'fp-ts/Either'
import {EnglishName} from "./english-name";

describe('EnglishName', () => {

  describe('requirement', () => {
    it('should create an instance', () => {
      expect(new EnglishName('EnglishName')).toBeTruthy();
    });

    it('should throw error when value length is 101.', () => {
      const value = 'a'.repeat(101);
      expect(value.length).toBe(101);
      expect(() => new EnglishName(value)).toThrow();
    });

    it('should throw error when value is contains not alphanumerical value', () => {
      const value = 'ABCアイウeo';
      expect(() => new EnglishName(value)).toThrow();
    });
  });

  describe('validate', () => {
    it('should return left when value length is 101.', () => {
      const value = 'a'.repeat(101);
      expect(value.length).toBe(101);
      const actual = EnglishName.validate(value);
      expect(E.isLeft(actual)).toBeTrue();
    });

    it('should return right when value length is 100', () => {
      const value = 'a'.repeat(100);
      expect(value.length).toBe(100);
      const actual = EnglishName.validate(value);
      expect(actual).toEqual(E.right(new EnglishName(value)));
    });

    it('should return left when value contains not alphanumerical value', () => {
      const value = 'ABCあいうeo';
      const actual = EnglishName.validate(value);
      expect(E.isLeft(actual)).toBeTrue();
    })
  });

  describe('equals', () => {
    it('should return true when compare same underlying value instances', () => {
      const a = new EnglishName('NAME');
      const b = new EnglishName('NAME');
      expect(a === b).toBeFalse();
      expect(a.equals(b)).toBeTrue();
    });

    it('should return false when compare not same underlying value instances', () => {
      const a = new EnglishName('NAME');
      const b = new EnglishName('name');
      expect(a === b).toBeFalse();
      expect(a.equals(b)).toBeFalse();
    });
  });
});

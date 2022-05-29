import {expect} from "@angular/flex-layout/_private-utils/testing";
import * as E from 'fp-ts/Either'
import {UbiquitousName} from "./ubiquitous-name";

describe('UbiquitousName', () => {

  describe('requirement', () => {
    it('should create an instance', () => {
      expect(new UbiquitousName('sample bounded context name')).toBeTruthy();
    });

    it('should throw error when value length is 51.', () => {
      const value = 'a'.repeat(51);
      expect(value.length).toBe(51);
      expect(() => new UbiquitousName(value)).toThrow();
    });
  });

  describe('validate', () => {
    it('should return left when value length is 51.', () => {
      const value = 'a'.repeat(51);
      expect(value.length).toBe(51);
      const actual = UbiquitousName.validate(value);
      expect(E.isLeft(actual)).toBeTrue();
    });

    it('should return right when value length is 50', () => {
      const value = 'a'.repeat(50);
      expect(value.length).toBe(50);
      const actual = UbiquitousName.validate(value);
      expect(actual).toEqual(E.right(new UbiquitousName(value)));
    });
  });

  describe('equals', () => {
    it('should return true when compare same underlying value instances', () => {
      const a = new UbiquitousName('NAME');
      const b = new UbiquitousName('NAME');
      expect(a === b).toBeFalse();
      expect(a.equals(b)).toBeTrue();
    });

    it('should return false when compare not same underlying value instances', () => {
      const a = new UbiquitousName('NAME');
      const b = new UbiquitousName('name');
      expect(a === b).toBeFalse();
      expect(a.equals(b)).toBeFalse();
    });
  });
});

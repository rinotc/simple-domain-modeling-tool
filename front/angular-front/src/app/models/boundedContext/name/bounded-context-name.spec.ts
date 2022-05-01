import {BoundedContextName} from './bounded-context-name';
import {expect} from "@angular/flex-layout/_private-utils/testing";
import * as E from 'fp-ts/Either'

describe('BoundedContextName', () => {

  describe('requirement', () => {
    it('should create an instance', () => {
      expect(new BoundedContextName('sample bounded context name')).toBeTruthy();
    });

    it('should throw error when value length is 101.', () => {
      const value = 'a'.repeat(101);
      expect(value.length).toBe(101);
      expect(() => new BoundedContextName(value)).toThrow();
    });
  });

  describe('validate', () => {
    it('should return left when value length is 101.', () => {
      const value = 'a'.repeat(101);
      expect(value.length).toBe(101);
      const actual = BoundedContextName.validate(value);
      expect(E.isLeft(actual)).toBeTrue();
    });

    it('should return right when value length is 100', () => {
      const value = 'a'.repeat(100);
      expect(value.length).toBe(100);
      const actual = BoundedContextName.validate(value);
      expect(actual).toEqual(E.right(new BoundedContextName(value)));
    });
  });

  describe('equals', () => {
    it('should return true when compare same underlying value instances', () => {
      const a = new BoundedContextName('NAME');
      const b = new BoundedContextName('NAME');
      expect(a === b).toBeFalse();
      expect(a.equals(b)).toBeTrue();
    });

    it('should return false when compare not same underlying value instances', () => {
      const a = new BoundedContextName('NAME');
      const b = new BoundedContextName('name');
      expect(a === b).toBeFalse();
      expect(a.equals(b)).toBeFalse();
    });
  });
});

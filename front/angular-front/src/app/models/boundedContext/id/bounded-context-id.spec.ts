import {BoundedContextId} from './bounded-context-id';
import {expect} from "@angular/flex-layout/_private-utils/testing";
import * as E from "fp-ts/Either";

describe('BoundedContextId', () => {

  describe('requirement', () => {
    it('should create an instance', () => {
      const value = 'abcde-fghij-klmno-pqrst-uvwxy-z12345'
      expect(value.length).toBe(36);
      expect(new BoundedContextId(value)).toBeTruthy();
    });

    it('should throw error when value length is 35.', () => {
      const value = 'abcde-fghij-klmno-pqrst-uvwxy-z1234'
      expect(value.length).toBe(35);
      expect(() => new BoundedContextId(value)).toThrow();
    });
  });

  describe('validate', () => {
    it('should return Left when value length is 35.', () => {
      const value = 'abcde-fghij-klmno-pqrst-uvwxy-z1234'
      expect(value.length).toBe(35);
      const actual = BoundedContextId.validate(value);
      expect(E.isLeft(actual)).toBeTrue();
    });

    it('should return right when value length is 36', () => {
      const value = 'abcde-fghij-klmno-pqrst-uvwxy-z12345'
      expect(value.length).toBe(36);
      expect(BoundedContextId.validate(value)).toEqual(E.right(new BoundedContextId(value)));
    });
  });

  describe('equals', () => {
    it('should return true when compare same underlying value instances', () => {
      const valueA = 'abcde-fghij-klmno-pqrst-uvwxy-z12345';
      const valueB = 'abcde-fghij-klmno-pqrst-uvwxy-z12345';
      const a = new BoundedContextId(valueA);
      const b = new BoundedContextId(valueB);
      expect(valueA === valueB).toBeTrue();
      expect(a === b).toBeFalse();
      expect(a.equals(b)).toBeTrue();
    });

    it('should return false when compare not same underlying value instances', () => {
      const valueA = 'abcde-fghij-klmno-pqrst-uvwxy-z12345';
      const valueB = 'abcde-fghij-klmno-pqrst-uvwxy-z12349';
      const a = new BoundedContextId(valueA);
      const b = new BoundedContextId(valueB);
      expect(a === b).toBeFalse();
      expect(a.equals(b)).toBeFalse();
    });
  });
});

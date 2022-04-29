import {ProjectId} from './project-id';
import {expect} from "@angular/flex-layout/_private-utils/testing";
import * as E from "fp-ts/Either";

describe('ProjectId', () => {

  describe('requirement', () => {
    it('should create an instance', () => {
      const value = 'abcde-fghij-klmno-pqrst-uvwxy-z12345'
      expect(value.length).toBe(36);
      expect(new ProjectId(value)).toBeTruthy();
    });

    it('should throw error when value length is 35.', () => {
      const value = 'abcde-fghij-klmno-pqrst-uvwxy-z1234'
      expect(value.length).toBe(35);
      expect(() => new ProjectId(value)).toThrow();
    });
  });

  describe('validate', () => {
    it('should return Left when value length is 35.', () => {
      const value = 'abcde-fghij-klmno-pqrst-uvwxy-z1234'
      expect(value.length).toBe(35);
      const actual = ProjectId.validate(value);
      expect(E.isLeft(actual)).toBeTrue();
    });

    it('should return right when value length is 36', () => {
      const value = 'abcde-fghij-klmno-pqrst-uvwxy-z12345'
      expect(value.length).toBe(36);
      expect(ProjectId.validate(value)).toEqual(E.right(new ProjectId(value)));
    });
  });

  describe('equals', () => {
    it('should return true when compare same underlying value instances', () => {
      const valueA = 'abcde-fghij-klmno-pqrst-uvwxy-z12345';
      const valueB = 'abcde-fghij-klmno-pqrst-uvwxy-z12345';
      const a = new ProjectId(valueA);
      const b = new ProjectId(valueB);
      expect(valueA === valueB).toBeTrue();
      expect(a === b).toBeFalse();
      expect(a.equals(b)).toBeTrue();
    });

    it('should return false when compare not same underlying value instances', () => {
      const valueA = 'abcde-fghij-klmno-pqrst-uvwxy-z12345';
      const valueB = 'abcde-fghij-klmno-pqrst-uvwxy-z12349';
      const a = new ProjectId(valueA);
      const b = new ProjectId(valueB);
      expect(a === b).toBeFalse();
      expect(a.equals(b)).toBeFalse();
    });
  });
});

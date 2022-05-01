import {ProjectOverview} from './project-overview';
import {expect} from "@angular/flex-layout/_private-utils/testing";
import * as E from 'fp-ts/Either';

describe('ProjectOverview', () => {

  describe('requirement', () => {
    it('should create an instance when value length 500', () => {
      const value = 'あ'.repeat(500);
      expect(value.length).toBe(500);
      expect(new ProjectOverview(value)).toBeTruthy();
    });

    it('should throw error when value length is 501', () => {
      const value = 'あ'.repeat(501);
      expect(value.length).toBe(501);
      expect(() => new ProjectOverview(value)).toThrow();
    });
  });

  describe('equals', () => {
    it('should return true when compare same underlying value instances', () => {
      const a = new ProjectOverview('アイウエオ');
      const b = new ProjectOverview('アイウエオ');
      expect(a === b).toBeFalse();
      expect(a.equals(b)).toBeTrue();
    });

    it('should return false when compare not same underlying value instances', () => {
      const a = new ProjectOverview('アイウエオ');
      const b = new ProjectOverview('カキクケコ');
      expect(a === b).toBeFalse();
      expect(a.equals(b)).toBeFalse();
    });
  });

  describe('validate', () => {
    it('should return left when value length is 501', () => {
      const value = 'あ'.repeat(501);
      expect(value.length).toBe(501);
      const actual = ProjectOverview.validate(value);
      expect(E.isLeft(actual)).toBeTrue();
    });

    it('should return right when value length is 500', () => {
      const value = 'あ'.repeat(500);
      expect(value.length).toBe(500);
      const actual = ProjectOverview.validate(value);
      expect(actual).toEqual(E.right(new ProjectOverview(value)));
    });
  });
});

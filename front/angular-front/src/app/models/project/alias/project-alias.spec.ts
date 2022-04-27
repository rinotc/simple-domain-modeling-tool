import {ProjectAlias} from './project-alias';
import {expect} from "@angular/flex-layout/_private-utils/testing";
import {ProjectId} from "../id/project-id";
import * as E from 'fp-ts/Either';

describe('ProjectAlias', () => {

  describe('requirement', () => {
    it('should throw error when value length is 0', () => {
      const value = '';
      expect(value.length).toBe(0);
      expect(() => new ProjectId(value)).toThrow();
    });

    it('should throw error when value length is 33', () => {
      const value = 'A'.repeat(33);
      expect(value.length).toBe(33);
      expect(() => new ProjectAlias(value)).toThrow();
    });

    it('should throw requirement error when value has non alphanumerical characters', () => {
      const kana = 'アイウエオ';
      const hiragana = 'あいうえお';
      const containSymbol = 'abcde-fghij';
      expect(() => new ProjectAlias(kana)).toThrow();
      expect(() => new ProjectAlias(hiragana)).toThrow();
      expect(() => new ProjectAlias(containSymbol)).toThrow();
    });

    it('should create instance when value length is 32 and using alphanumerical characters', () => {
      const value = 'abcdeABCDEvwxyzVWXYZ1234567890gh';
      expect(value.length).toBe(32);
      expect(new ProjectAlias(value)).toBeTruthy();
    });
  });

  describe('validate', () => {
    it('should return left when value is empty', () => {
      const value = '';
      expect(value.length).toBe(0);
      const actual = ProjectAlias.validate(value);
      expect(E.isLeft(actual)).toBeTrue();
    });

    it('should return left when value length is 33', () => {
      const value = 'A'.repeat(33);
      expect(value.length).toBe(33);
      const actual = ProjectAlias.validate(value);
      expect(E.isLeft(actual)).toBeTrue();
    });

    it('should return left when value has non alphanumerical characters', () => {
      const kana = 'アイウエオ';
      const hiragana = 'あいうえお';
      const containSymbol = 'abcde-fghij';
      expect(E.isLeft(ProjectAlias.validate(kana))).toBeTrue();
      expect(E.isLeft(ProjectAlias.validate(hiragana))).toBeTrue();
      expect(E.isLeft(ProjectAlias.validate(containSymbol))).toBeTrue();
    });

    it('should return right when value length is 32 and alphanumerical characters.', () => {
      const value = 'abcdeABCDEvwxyzVWXYZ1234567890gh';
      expect(value.length).toBe(32);
      const actual = ProjectAlias.validate(value);
      expect(actual).toEqual(E.right(new ProjectAlias(value)));
    });
  });

  describe('equals', () => {
    it('should return true when compare same underlying value instances', () => {
      const a = new ProjectAlias('ABC');
      const b = new ProjectAlias('ABC');
      expect(a === b).toBeFalse(); // 異なるインスタンスである
      expect(a.equals(b)).toBeTrue(); // 値が等しければ同値とみなす
    });
  });
});

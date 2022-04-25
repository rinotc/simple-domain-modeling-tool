import { ProjectName } from './project-name';
import {expect} from "@angular/flex-layout/_private-utils/testing";
import * as E from 'fp-ts/Either'

describe('ProjectName', () => {

  describe('requirement', () => {
    it('should create an instance', () => {
      expect(new ProjectName('sample project name')).toBeTruthy();
    });

    it('should throw error when value length is 101.', () => {
      const value = 'a'.repeat(101);
      expect(value.length).toBe(101);
      expect(() => new ProjectName(value)).toThrow();
    });
  });

  describe('validate', () => {
    it('should return left Left when value length is 101.', () => {
      const value = 'a'.repeat(101);
      expect(value.length).toBe(101);
      const actual = ProjectName.validate(value);
      expect(E.isLeft(actual)).toBeTrue();
    });

    it('should return right when value length is 100', () => {
      const value = 'a'.repeat(100);
      expect(value.length).toBe(100);
      const actual = ProjectName.validate(value);
      expect(actual).toEqual(E.right(new ProjectName(value)));
    });
  });
});

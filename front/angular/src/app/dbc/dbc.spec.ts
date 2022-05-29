import { expect } from '@angular/flex-layout/_private-utils/testing';
import { notNull, requirement, RequirementError } from './dbc';

describe('dbc', () => {
  describe('nutNull', () => {
    it('should throw RequirementError when passed value is null', () => {
      expect(() => notNull(null, 'message')).toThrow(
        new RequirementError('not null error: message')
      );
    });

    it('should throw RequirementError when passed value is undefined', () => {
      expect(() => notNull(undefined, 'message')).toThrow(
        new RequirementError('not null error: message')
      );
    });

    it('should not throw RequirementError when value is not null or undefined.', () => {
      expect(() => notNull(1, 'message')).toBeTruthy();
    });
  });

  describe('requirement', () => {
    it('should throw RequirementError when condition is false', () => {
      expect(() => requirement(false, 'message')).toThrow(
        new RequirementError('message')
      );
    });

    it('should not throw RequirementError when condition is true', () => {
      expect(() => requirement(true, 'message')).toBeTruthy();
    });
  });
});

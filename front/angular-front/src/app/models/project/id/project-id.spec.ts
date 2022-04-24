import { ProjectId } from './project-id';
import {expect} from "@angular/flex-layout/_private-utils/testing";

describe('ProjectId', () => {
  it('should create an instance', () => {
    const value = 'abcde-fghij-klmno-pqrst-uvwxy-z12345'
    expect(value.length).toBe(36);
    expect(new ProjectId(value)).toBeTruthy();
  });

  it('should throw error when value is 35.', () => {
    const value = 'abcde-fghij-klmno-pqrst-uvwxy-z1234'
    expect(value.length).toBe(35);
    expect(() => new ProjectId(value)).toThrow();
  });
});

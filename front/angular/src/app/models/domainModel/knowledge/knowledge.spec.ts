import {Knowledge} from "./knowledge";
import {expect} from "@angular/flex-layout/_private-utils/testing";

describe('Knowledge', () => {

  describe('equals', () => {
    it('should return true when compare same underlying value instances', () => {
      const a = new Knowledge('知識')
      const b = new Knowledge('知識')
      expect(a === b).toBeFalse();
      expect(a.equals(b)).toBeTrue();
    })
  });
});

import { expect } from '@angular/flex-layout/_private-utils/testing';
import { Password } from './password';

describe('Password', () => {
  describe('仕様のテスト', () => {
    describe('パスワードは8文字以上50文字以下の文字列', () => {
      it('パスワードが7文字の時、例外を投げる', () => {
        expect(() => new Password('Abc123@')).toThrow();
      });

      it('パスワードが8文字の時、インスタンスを生成できる', () => {
        expect(new Password('Abc123@#')).toBeTruthy();
      });

      it('パスワードが51文字の時、例外を投げる', () => {
        const plainPassword = 'Abc123@#$%'.repeat(5) + 'x';
        expect(plainPassword.length).toBe(51);
        expect(() => new Password(plainPassword)).toThrow();
      });

      it('パスワードが50文字の時、インスタンスを生成できる', () => {
        const plainPassword = 'Abc123@#$%'.repeat(5);
        expect(plainPassword.length).toBe(50);
        expect(new Password(plainPassword)).toBeTruthy();
      });
    });

    describe('パスワードは英大文字、小文字、数字、記号を少なくとも一つ含む', () => {
      it('英大文字を含まない時、例外を投げる', () => {
        const plainPassword = 'abc123@#$%';
        expect(() => new Password(plainPassword)).toThrow();
      });

      it('英小文字を含まない時、例外を投げる', () => {
        const plainPassword = 'ABC123@#$%';
        expect(() => new Password(plainPassword)).toThrow();
      });

      it('数字を含まない時、例外を投げる', () => {
        const plainPassword = 'AbcDef@#$%';
        expect(() => new Password(plainPassword)).toThrow();
      });

      it('記号を含まない時、例外を投げる', () => {
        const plainPassword = 'Abc123Def';
        expect(() => new Password(plainPassword)).toThrow();
      });

      it('パスワードに日本語を含むとき、例外を投げる', () => {
        const plainPassword = 'Abc123@#$アイウエオ';
        expect(() => new Password(plainPassword)).toThrow();
      });
    });
  });
});

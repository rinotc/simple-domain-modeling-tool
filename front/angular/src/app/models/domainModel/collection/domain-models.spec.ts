import { DomainModel } from '../domain-model';
import { DomainModelId } from '../id/domain-model-id';
import { BoundedContextId } from '../../boundedContext/id/bounded-context-id';
import { UbiquitousName } from '../ubiquitousName/ubiquitous-name';
import { EnglishName } from '../englishName/english-name';
import { Knowledge } from '../knowledge/knowledge';
import { DomainModels } from './domain-models';
import { expect } from '@angular/flex-layout/_private-utils/testing';

describe('DomainModels', () => {
  describe('事前条件', () => {
    describe('重複したIDの要素は存在できない', () => {
      it('重複したIDの要素がある配列でインスタンスを生成しようとすると、例外を投げる', () => {
        const dm1 = new DomainModel(
          new DomainModelId('abcde-fghij-klmno-pqrst-uvwxy-z12345'),
          new BoundedContextId('xxxxx-xxxxx-xxxxx-xxxxx-xxxxx-x12345'),
          new UbiquitousName('ユビキタス名称'),
          new EnglishName('UbiqitousName'),
          new Knowledge('知識')
        );

        const dm2 = new DomainModel(
          dm1.id,
          new BoundedContextId('xxxxx-xxxxx-xxxxx-xxxxx-xxxxx-x12349'),
          new UbiquitousName('ユビキタス名称2'),
          new EnglishName('UbiqitousName2'),
          new Knowledge('知識2')
        );

        expect(() => new DomainModels([dm1, dm2])).toThrow();
      });

      it('IDが重複していない要素の配列でインスタンスが生成できる', () => {
        const dm1 = new DomainModel(
          new DomainModelId('abcde-fghij-klmno-pqrst-uvwxy-z12345'),
          new BoundedContextId('xxxxx-xxxxx-xxxxx-xxxxx-xxxxx-x12345'),
          new UbiquitousName('ユビキタス名称'),
          new EnglishName('UbiqitousName'),
          new Knowledge('知識')
        );

        const dm2 = new DomainModel(
          new DomainModelId('abcde-fghij-klmno-pqrst-uvwxy-z99999'),
          new BoundedContextId('xxxxx-xxxxx-xxxxx-xxxxx-xxxxx-x12349'),
          new UbiquitousName('ユビキタス名称2'),
          new EnglishName('UbiqitousName2'),
          new Knowledge('知識2')
        );

        expect(new DomainModels([dm1, dm2])).toBeTruthy();
      });
    });
  });

  describe('findById', () => {
    const dm1 = new DomainModel(
      new DomainModelId('abcde-fghij-klmno-pqrst-uvwxy-z12345'),
      new BoundedContextId('xxxxx-xxxxx-xxxxx-xxxxx-xxxxx-x12345'),
      new UbiquitousName('ユビキタス名称'),
      new EnglishName('UbiqitousName'),
      new Knowledge('知識')
    );

    const dm2 = new DomainModel(
      new DomainModelId('abcde-fghij-klmno-pqrst-uvwxy-z99999'),
      new BoundedContextId('xxxxx-xxxxx-xxxxx-xxxxx-xxxxx-x12349'),
      new UbiquitousName('ユビキタス名称2'),
      new EnglishName('UbiqitousName2'),
      new Knowledge('知識2')
    );

    const domainModels = new DomainModels([dm1, dm2]);
    it('IDと一致するドメインモデルを取得する', () => {
      const actual = domainModels.findById(dm2.id);
      expect(actual).toBe(dm2);
    });

    it('存在しないIDを渡した場合に、ドメインモデルは取得できない', () => {
      const actual = domainModels.findById(
        new DomainModelId('zzzzz-fghij-klmno-pqrst-uvwxy-z12345')
      );
      expect(actual).toBeUndefined();
    });
  });

  describe('findByEnglishName', () => {
    describe('英語名と境界づけられたコンテキストIDで一意なドメインモデルを取得する', () => {
      const dm1BoundedContextId = new BoundedContextId(
        'xxxxx-xxxxx-xxxxx-xxxxx-xxxxx-x12345'
      );
      const englishName1 = new EnglishName('UbiquitousName1');
      const dm2BoundedContextId = new BoundedContextId(
        'yyyyy-yyyyy-yyyyy-yyyyy-yyyyy-y12349'
      );
      const englishName2 = new EnglishName('UbiquitousName2');

      const dm1 = new DomainModel(
        new DomainModelId('abcde-fghij-klmno-pqrst-uvwxy-z12345'),
        dm1BoundedContextId,
        new UbiquitousName('ユビキタス名称1'),
        englishName1,
        new Knowledge('知識1')
      );

      const dm2 = new DomainModel(
        new DomainModelId('abcde-fghij-klmno-pqrst-uvwxy-z99999'),
        dm2BoundedContextId,
        new UbiquitousName('ユビキタス名称2'),
        englishName2,
        new Knowledge('知識2')
      );

      const domainModels = new DomainModels([dm1, dm2]);
      it('引数で渡した英語名と境界づけられたコンテキストIDの両者が一致するドメインモデルを取得する', () => {
        const actual = domainModels.findByEnglishName(
          englishName2,
          dm2BoundedContextId
        );

        expect(actual).toBe(dm2);
      });

      it('引数で渡した英語名と一致するドメインモデルがあっても、境界づけられたコンテキストIDが一致していなければ取得できない', () => {
        const notExistBoundedContextId = new BoundedContextId(
          'aaaaa-aaaaa-aaaaa-aaaaa-aaaaa-a12345'
        );
        domainModels.models.forEach((dm) => {
          expect(
            dm.boundedContextId.equals(notExistBoundedContextId)
          ).toBeFalse();
        });
        const actual = domainModels.findByEnglishName(
          englishName2,
          notExistBoundedContextId
        );

        expect(actual).toBeFalsy();
      });
    });
  });

  describe('append', () => {
    it('重複した要素がない時、引数に渡した要素を追加した新たなインスタンスを返す', () => {
      const dm1 = new DomainModel(
        new DomainModelId('abcde-fghij-klmno-pqrst-uvwxy-z12345'),
        new BoundedContextId('xxxxx-xxxxx-xxxxx-xxxxx-xxxxx-x12345'),
        new UbiquitousName('ユビキタス名称'),
        new EnglishName('UbiqitousName'),
        new Knowledge('知識')
      );
      const domainModels = new DomainModels([dm1]);

      const dm2 = new DomainModel(
        new DomainModelId('abcde-fghij-klmno-pqrst-uvwxy-z99999'),
        new BoundedContextId('xxxxx-xxxxx-xxxxx-xxxxx-xxxxx-x12349'),
        new UbiquitousName('ユビキタス名称2'),
        new EnglishName('UbiqitousName2'),
        new Knowledge('知識2')
      );

      const actual = domainModels.append(dm2);
      expect(actual.models.length).toBe(2);
    });

    it('重複した要素がある時、引数にわたした要素で置き換えた新しいインスタンスを返す', () => {
      const dm1 = new DomainModel(
        new DomainModelId('abcde-fghij-klmno-pqrst-uvwxy-z12345'),
        new BoundedContextId('xxxxx-xxxxx-xxxxx-xxxxx-xxxxx-x12345'),
        new UbiquitousName('ユビキタス名称'),
        new EnglishName('UbiqitousName'),
        new Knowledge('知識')
      );

      const dm2 = new DomainModel(
        new DomainModelId('abcde-fghij-klmno-pqrst-uvwxy-z99999'),
        new BoundedContextId('xxxxx-xxxxx-xxxxx-xxxxx-xxxxx-x12349'),
        new UbiquitousName('ユビキタス名称2'),
        new EnglishName('UbiqitousName2'),
        new Knowledge('知識2')
      );
      const domainModels = new DomainModels([dm1, dm2]);

      const newDm2 = new DomainModel(
        dm2.id,
        dm2.boundedContextId,
        new UbiquitousName('更新後ユビキタス名称2'),
        new EnglishName('UpdatedUbiqitousName2'),
        new Knowledge('更新後知識2')
      );

      const actual = domainModels.append(newDm2);
      expect(actual.models.length).toBe(2);
      expect(actual.findById(dm2.id)).not.toBe(dm2);
      expect(actual.findById(dm2.id)).toBe(newDm2);
    });
  });

  describe('replace', () => {
    it('引数に渡された配列で置き換える', () => {
      const dm1 = new DomainModel(
        new DomainModelId('abcde-fghij-klmno-pqrst-uvwxy-z12345'),
        new BoundedContextId('xxxxx-xxxxx-xxxxx-xxxxx-xxxxx-x12345'),
        new UbiquitousName('ユビキタス名称'),
        new EnglishName('UbiqitousName'),
        new Knowledge('知識')
      );

      const dm2 = new DomainModel(
        new DomainModelId('abcde-fghij-klmno-pqrst-uvwxy-z99999'),
        new BoundedContextId('xxxxx-xxxxx-xxxxx-xxxxx-xxxxx-x12349'),
        new UbiquitousName('ユビキタス名称2'),
        new EnglishName('UbiqitousName2'),
        new Knowledge('知識2')
      );
      const domainModels = new DomainModels([dm1, dm2]);

      const actual = domainModels.replace([dm1]);
      expect(actual.models.length).toBe(1);
    });
  });

  describe('empty', () => {
    it('空の配列でインスタンスを生成する', () => {
      const actual = DomainModels.empty();
      expect(actual.models.length).toBe(0);
    });
  });
});

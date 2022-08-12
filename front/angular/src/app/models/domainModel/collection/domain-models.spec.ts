import { DomainModel } from '../domain-model';
import { DomainModelId } from '../id/domain-model-id';
import { BoundedContextId } from '../../boundedContext/id/bounded-context-id';
import { UbiquitousName } from '../ubiquitousName/ubiquitous-name';
import { EnglishName } from '../englishName/english-name';
import { Knowledge } from '../knowledge/knowledge';
import { DomainModels } from './domain-models';

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

  describe('findById', () => {});

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
          ).toBeTrue();
        });
        const actual = domainModels.findByEnglishName(
          englishName2,
          notExistBoundedContextId
        );

        expect(actual).toBeFalsy();
      });
    });
  });

  describe('append', () => {});

  describe('replace', () => {});

  describe('empty', () => {});
});

import { DomainModel } from '../domain-model';
import { DomainModelId } from '../id/domain-model-id';
import { EnglishName } from '../englishName/english-name';
import { BoundedContextId } from '../../boundedContext/id/bounded-context-id';

export class DomainModels implements Iterable<DomainModel> {
  constructor(readonly _models: Array<DomainModel>) {
    if (!DomainModels.isValid(this)) {
      throw new TypeError(
        `DomainModels must not exists duplicate values: ${this._models}`
      );
    }
  }

  // https://qiita.com/suin/items/46bc92d9e228e915fa42
  [Symbol.iterator](): Iterator<DomainModel> {
    return this._models[Symbol.iterator]();
  }

  get models(): DomainModel[] {
    return this._models;
  }

  findById(id: DomainModelId): DomainModel | undefined {
    return this._models.find((dm) => dm.id.equals(id));
  }

  findByEnglishName(
    englishName: EnglishName,
    boundedContextId: BoundedContextId
  ): DomainModel | undefined {
    return this._models.find((dm) => {
      return (
        dm.englishName.equals(englishName) &&
        dm.boundedContextId.equals(boundedContextId)
      );
    });
  }

  append(domainModel: DomainModel): DomainModels {
    let isConflict = false;
    const dms: DomainModel[] = [];
    for (const dm of this) {
      if (dm.equals(domainModel)) {
        isConflict = true;
        dms.push(domainModel);
      } else {
        dms.push(dm);
      }
    }
    if (!isConflict) {
      dms.push(domainModel);
    }
    return new DomainModels(dms);
  }

  /**
   * 引数で渡された配列で置き換える
   * @param domainModels ドメインモデルの配列
   */
  replace(domainModels: DomainModel[]): DomainModels {
    return new DomainModels(domainModels);
  }

  /**
   * 空の要素を持つドメインモデルのファーストクラスコレクションを返す
   */
  static empty(): DomainModels {
    return new DomainModels([]);
  }

  private static isValid(models: DomainModels): boolean {
    return this.mustNotExistDuplicateValues(models);
  }

  // 重複した要素は存在できない
  private static mustNotExistDuplicateValues(models: DomainModels): boolean {
    const idStrings = models.models.map((c) => c.id.value);
    const idSet = new Set(idStrings);
    return idStrings.length === idSet.size;
  }
}

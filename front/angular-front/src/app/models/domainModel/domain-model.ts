import {DomainModelId} from "./id/domain-model-id";
import {UbiquitousName} from "./ubiquitousName/ubiquitous-name";
import {EnglishName} from "./englishName/english-name";
import {Knowledge} from "./knowledge/knowledge";

export class DomainModel {

  constructor(
    readonly id: DomainModelId,
    readonly ubiquitousName: UbiquitousName,
    readonly englishName: EnglishName,
    readonly knowledge: Knowledge
  ) {}

  equals(other: DomainModel): boolean {
    return this.id.equals(other.id);
  }
}

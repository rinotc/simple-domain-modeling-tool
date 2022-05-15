import {DomainModelId} from "./id/domain-model-id";
import {UbiquitousName} from "./ubiquitousName/ubiquitous-name";
import {EnglishName} from "./englishName/english-name";
import {Knowledge} from "./knowledge/knowledge";
import {BoundedContextId} from "../boundedContext/id/bounded-context-id";

export class DomainModel {

  constructor(
    readonly id: DomainModelId,
    readonly boundedContextId: BoundedContextId,
    readonly ubiquitousName: UbiquitousName,
    readonly englishName: EnglishName,
    readonly knowledge: Knowledge
  ) {}

  equals(other: DomainModel): boolean {
    return this.id.equals(other.id);
  }
}

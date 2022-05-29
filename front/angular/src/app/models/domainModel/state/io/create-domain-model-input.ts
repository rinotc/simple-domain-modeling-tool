import { BoundedContextId } from '../../../boundedContext/id/bounded-context-id';
import { UbiquitousName } from '../../ubiquitousName/ubiquitous-name';
import { EnglishName } from '../../englishName/english-name';
import { Knowledge } from '../../knowledge/knowledge';

export interface CreateDomainModelInput {
  boundedContextId: BoundedContextId;
  ubiquitousName: UbiquitousName;
  englishName: EnglishName;
  knowledge: Knowledge;
}

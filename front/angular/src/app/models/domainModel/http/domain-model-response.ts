import { DomainModel } from '../domain-model';
import { BoundedContextId } from '../../boundedContext/id/bounded-context-id';
import { UbiquitousName } from '../ubiquitousName/ubiquitous-name';
import { EnglishName } from '../englishName/english-name';
import { Knowledge } from '../knowledge/knowledge';
import { DomainModelId } from '../id/domain-model-id';

export interface DomainModelResponse {
  id: string;
  boundedContextId: string;
  ubiquitousName: string;
  englishName: string;
  knowledge: string;
}

export const DomainModelResponse = {
  translate(res: DomainModelResponse): DomainModel {
    return new DomainModel(
      new DomainModelId(res.id),
      new BoundedContextId(res.boundedContextId),
      new UbiquitousName(res.ubiquitousName),
      new EnglishName(res.englishName),
      new Knowledge(res.knowledge)
    );
  },
};

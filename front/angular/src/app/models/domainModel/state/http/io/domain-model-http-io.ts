import { BoundedContextId } from '../../../../boundedContext/id/bounded-context-id';
import { UbiquitousName } from '../../../ubiquitousName/ubiquitous-name';
import { EnglishName } from '../../../englishName/english-name';
import { Knowledge } from '../../../knowledge/knowledge';
import { DomainModel } from '../../../domain-model';
import { DomainModelId } from '../../../id/domain-model-id';
import { requirement } from '../../../../../dbc/dbc';

export interface DomainModelHttpIO {
  id: string | undefined;
  boundedContextId: string;
  ubiquitousName: string;
  englishName: string;
  knowledge: string;
}

export const DomainModelHttpIO = {
  create(
    boundedContextId: BoundedContextId,
    ubiquitousName: UbiquitousName,
    englishName: EnglishName,
    knowledge: Knowledge
  ): DomainModelHttpIO {
    return {
      id: undefined,
      boundedContextId: boundedContextId.value,
      ubiquitousName: ubiquitousName.value,
      englishName: englishName.value,
      knowledge: knowledge.value,
    };
  },

  update(domainModel: DomainModel): DomainModelHttpIO {
    return {
      id: domainModel.id.value,
      boundedContextId: domainModel.boundedContextId.value,
      ubiquitousName: domainModel.ubiquitousName.value,
      englishName: domainModel.englishName.value,
      knowledge: domainModel.knowledge.value,
    };
  },

  convert(io: DomainModelHttpIO): DomainModel {
    requirement(!!io.id, 'id must be defined');
    return new DomainModel(
      new DomainModelId(io.id!),
      new BoundedContextId(io.boundedContextId),
      new UbiquitousName(io.ubiquitousName),
      new EnglishName(io.englishName),
      new Knowledge(io.knowledge)
    );
  },
};

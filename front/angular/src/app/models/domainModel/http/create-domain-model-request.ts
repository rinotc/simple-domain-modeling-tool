import { CreateDomainModelInput } from '../state/io/create-domain-model-input';

export interface CreateDomainModelRequest {
  ubiquitousName: string;
  englishName: string;
  knowledge: string;
}

export const CreateDomainModelRequest = {
  from(input: CreateDomainModelInput): CreateDomainModelRequest {
    return {
      ubiquitousName: input.ubiquitousName.value,
      englishName: input.englishName.value,
      knowledge: input.knowledge.value,
    };
  },
};

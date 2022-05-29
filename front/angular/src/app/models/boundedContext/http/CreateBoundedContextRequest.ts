import { BoundedContextAlias } from '../alias/bounded-context-alias';
import { BoundedContextName } from '../name/bounded-context-name';
import { BoundedContextOverview } from '../overview/bounded-context-overview';

export type CreateBoundedContextRequest = {
  alias: string;
  name: string;
  overview: string;
};
export const CreateBoundedContextRequest = {
  translate(
    alias: BoundedContextAlias,
    name: BoundedContextName,
    overview: BoundedContextOverview
  ): CreateBoundedContextRequest {
    return {
      alias: alias.value,
      name: name.value,
      overview: overview.value,
    };
  },
};

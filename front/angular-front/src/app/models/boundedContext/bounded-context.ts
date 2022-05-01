import {BoundedContextId} from "./id/bounded-context-id";
import {BoundedContextAlias} from "./alias/bounded-context-alias";
import {BoundedContextName} from "./name/bounded-context-name";
import {BoundedContextOverview} from "./overview/bounded-context-overview";

export class BoundedContext {

  constructor(
    readonly id: BoundedContextId,
    readonly alias: BoundedContextAlias,
    readonly name: BoundedContextName,
    readonly overview: BoundedContextOverview
  ) {}

  equals(other: BoundedContext): boolean {
    return this.id.equals(other.id);
  }
}

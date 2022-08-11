import { BoundedContext } from './bounded-context';
import { BoundedContextAlias } from './alias/bounded-context-alias';
import * as O from 'fp-ts/Option';
import { BoundedContextId } from './id/bounded-context-id';

export class BoundedContexts {
  constructor(private readonly _contexts: BoundedContext[]) {}

  get contexts(): BoundedContext[] {
    return this._contexts;
  }

  findById(id: BoundedContextId): O.Option<BoundedContext> {
    const maybeBc = this._contexts.find((c) => c.id.equals(id));
    return O.fromNullable(maybeBc);
  }

  findByAlias(alias: BoundedContextAlias): BoundedContext | undefined {
    return this._contexts.find((c) => c.alias.equals(alias));
  }

  append(context: BoundedContext): BoundedContexts {
    let isConflict = false;
    const contexts: BoundedContext[] = [];
    for (const c of this._contexts) {
      if (c.equals(context)) {
        isConflict = true;
        contexts.push(context);
      } else {
        contexts.push(c);
      }
    }
    if (!isConflict) {
      contexts.push(context);
    }
    return new BoundedContexts(contexts);
  }

  replace(contexts: BoundedContext[]): BoundedContexts {
    return new BoundedContexts(contexts);
  }

  excludeById(id: BoundedContextId): BoundedContexts {
    const newContexts = this._contexts.filter((c) => !c.id.equals(id));
    return new BoundedContexts(newContexts);
  }

  static empty(): BoundedContexts {
    return new BoundedContexts([]);
  }
}

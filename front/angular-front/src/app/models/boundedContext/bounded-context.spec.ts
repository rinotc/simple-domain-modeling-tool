import { BoundedContext } from './bounded-context';
import {BoundedContextId} from "./id/bounded-context-id";
import {BoundedContextAlias} from "./alias/bounded-context-alias";
import {BoundedContextName} from "./name/bounded-context-name";
import {BoundedContextOverview} from "./overview/bounded-context-overview";

describe('BoundedContexts', () => {
  it('should create an instance', () => {
    const boundedContextId = new BoundedContextId('abcde-fghij-klmno-pqrst-uvwxy-z12345');
    const boundedContextAlias = new BoundedContextAlias('ALIAS');
    const boundedContextName = new BoundedContextName('境界づけられたコンテキスト名称');
    const boundedContextOverview = new BoundedContextOverview('境界づけられたコンテキスト概要');
    expect(new BoundedContext(boundedContextId, boundedContextAlias, boundedContextName, boundedContextOverview)).toBeTruthy();
  });
});

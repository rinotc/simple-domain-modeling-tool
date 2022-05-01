import { BoundedContext } from './bounded-context';
import {BoundedContextId} from "./id/bounded-context-id";
import {BoundedContextAlias} from "./alias/bounded-context-alias";
import {BoundedContextName} from "./name/bounded-context-name";
import {BoundedContextOverview} from "./overview/bounded-context-overview";

describe('BoundedContexts', () => {
  it('should create an instance', () => {
    const projectId = new BoundedContextId('abcde-fghij-klmno-pqrst-uvwxy-z12345');
    const projectAlias = new BoundedContextAlias('ALIAS');
    const projectName = new BoundedContextName('境界づけられたコンテキスト名称');
    const projectOverview = new BoundedContextOverview('境界づけられたコンテキスト概要');
    expect(new BoundedContext(projectId, projectAlias, projectName, projectOverview)).toBeTruthy();
  });
});

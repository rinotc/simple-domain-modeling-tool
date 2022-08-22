import { BoundedContext } from './bounded-context';
import { BoundedContextId } from './id/bounded-context-id';
import { BoundedContextAlias } from './alias/bounded-context-alias';
import { BoundedContextName } from './name/bounded-context-name';
import { BoundedContextOverview } from './overview/bounded-context-overview';

describe('BoundedContext', () => {
  it('should create an instance', () => {
    const id = new BoundedContextId('abcde-fghij-klmno-pqrst-uvwxy-z12345');
    const alias = new BoundedContextAlias('ALIAS');
    const name = new BoundedContextName('境界づけられたコンテキスト名称');
    const overview = new BoundedContextOverview(
      '境界づけられたコンテキスト概要'
    );
    expect(new BoundedContext(id, alias, name, overview)).toBeTruthy();
  });
});

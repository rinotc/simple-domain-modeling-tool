import { Component, OnInit } from '@angular/core';
import { BoundedContext } from '../../../../models/boundedContext/bounded-context';
import { BoundedContextsQuery } from '../../../../models/boundedContext/state/bounded-contexts.query';
import { BoundedContextsService } from '../../../../models/boundedContext/state/bounded-contexts.service';

@Component({
  selector: 'app-bounded-context-list-component',
  templateUrl: './bounded-context-list-component.component.html',
  styleUrls: ['./bounded-context-list-component.component.scss'],
})
export class BoundedContextListComponentComponent implements OnInit {
  boundedContexts: BoundedContext[] = [];

  displayedColumns: string[] = ['alias', 'name', 'overview', 'detail'];

  constructor(
    private boundedContextsService: BoundedContextsService,
    private boundedContextsQuery: BoundedContextsQuery
  ) {
    this.boundedContextsService.fetchAll();
    this.boundedContextsQuery.contexts$.subscribe((bcs) => {
      this.boundedContexts = bcs.contexts;
    });
  }

  ngOnInit() {}
}

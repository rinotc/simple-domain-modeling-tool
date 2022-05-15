import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {HeaderService} from "../../../../store/title/header.service";
import {notNull, requirement} from "../../../../dbc/dbc";
import {BoundedContextAlias} from "../../../../models/boundedContext/alias/bounded-context-alias";
import {BoundedContext} from "../../../../models/boundedContext/bounded-context";
import {BoundedContextsQuery} from "../../../../models/boundedContext/state/bounded-contexts.query";
import {BoundedContextsService} from "../../../../models/boundedContext/state/bounded-contexts.service";
import * as O from 'fp-ts/Option';

@Component({
  selector: 'app-project-detail',
  templateUrl: './bounded-context-detail-page.component.html',
  styleUrls: ['./bounded-context-detail-page.component.scss']
})
export class BoundedContextDetailPageComponent implements OnInit {

  private _boundedContext: BoundedContext | null = null;

  constructor(
    private route: ActivatedRoute,
    private headerService: HeaderService,
    private boundedContextsService: BoundedContextsService,
    private boundedContextsQuery: BoundedContextsQuery
  ) {}

  ngOnInit(): void {
    const aliasString = this.route.snapshot.paramMap.get('boundedContextAlias');
    notNull(aliasString, `alias string must not be null but ${aliasString}`);
    const alias = new BoundedContextAlias(aliasString!);
    this.boundedContextsQuery.contexts$.subscribe(contexts => {
      const maybeContext: O.Option<BoundedContext> = contexts.findByAlias(alias)
      O.match<BoundedContext, void>(
        () => {
          this.boundedContextsService.fetchAll();
        },
        (context) => {
          this.boundedContextsService.fetchById(context.id)
          this.headerService.update(context.name.value);
          this._boundedContext = context;
        }
      )(maybeContext)
    });
  }

  get isLoading(): boolean {
    return this._boundedContext == null;
  }

  get boundedContext(): BoundedContext {
    requirement(!this.isLoading, 'assume this method call after loading.');
    return this._boundedContext!;
  }
}

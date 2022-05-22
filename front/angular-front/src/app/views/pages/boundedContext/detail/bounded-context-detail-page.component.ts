import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {HeaderService} from "../../../../store/title/header.service";
import {notNull, requirement} from "../../../../dbc/dbc";
import {BoundedContextAlias} from "../../../../models/boundedContext/alias/bounded-context-alias";
import {BoundedContext} from "../../../../models/boundedContext/bounded-context";
import {BoundedContextsQuery} from "../../../../models/boundedContext/state/bounded-contexts.query";
import {BoundedContextsService} from "../../../../models/boundedContext/state/bounded-contexts.service";

@Component({
  selector: 'app-project-detail',
  templateUrl: './bounded-context-detail-page.component.html',
  styleUrls: ['./bounded-context-detail-page.component.scss']
})
export class BoundedContextDetailPageComponent implements OnInit {

  private _boundedContext: BoundedContext | null = null;

  constructor(
    private router: Router,
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
      const context = contexts.findByAlias(alias)
      if (context !== undefined) {
        this.boundedContextsService.fetchById(context.id)
        this.headerService.update(context.name.value);
        this._boundedContext = context;
      } else {
        this.boundedContextsService.fetchAll();
      }
    });
  }

  get isLoading(): boolean {
    return this._boundedContext == null;
  }

  get boundedContext(): BoundedContext {
    requirement(!this.isLoading, 'assume this method call after loading.');
    return this._boundedContext!;
  }

  clickCreateDomainModelButton(): void {
    this.router.navigateByUrl(`bounded-contexts/${this.boundedContext.alias.value}/domain-models/create`).then(isSuccess => {
      if (isSuccess) {
        console.log('success!')
      } else {
        console.log('failed!')
      }
    });
  }
}

import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {BoundedContextsService} from "../../../../models/boundedContext/state/bounded-contexts.service";
import {notNull, requirement} from "../../../../dbc/dbc";
import {BoundedContextAlias} from "../../../../models/boundedContext/alias/bounded-context-alias";
import {BoundedContextsQuery} from "../../../../models/boundedContext/state/bounded-contexts.query";
import {BoundedContext} from "../../../../models/boundedContext/bounded-context";
import {BoundedContextName} from "../../../../models/boundedContext/name/bounded-context-name";
import {BoundedContextOverview} from "../../../../models/boundedContext/overview/bounded-context-overview";
import {lastValueFrom} from "rxjs";

@Component({
  selector: 'app-bounded-context-update-page',
  templateUrl: './bounded-context-update-page.component.html',
  styleUrls: ['./bounded-context-update-page.component.scss']
})
export class BoundedContextUpdatePageComponent implements OnInit {

  private targetBoundedContext: BoundedContext | null = null;

  inputBoundedContextName: string = '';
  inputBoundedContextAlias: string = '';
  inputBoundedContextOverview: string = '';

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private boundedContextsService: BoundedContextsService,
    private boundedContextsQuery: BoundedContextsQuery
  ) { }

  ngOnInit(): void {
    const aliasString = this.route.snapshot.paramMap.get('boundedContextAlias');
    notNull(aliasString, `alias string must not be null but ${aliasString}`);
    const alias = new BoundedContextAlias(aliasString!);
    this.boundedContextsQuery.contexts$.subscribe(contexts => {
      const context = contexts.findByAlias(alias);
      if (context !== undefined) {
        this.boundedContextsService.fetchById(context.id);
        this.targetBoundedContext = context;
        this.inputBoundedContextAlias = context.alias.value;
        this.inputBoundedContextName = context.name.value;
        this.inputBoundedContextOverview = context.overview.value;
      } else {
        this.boundedContextsService.fetchAll();
      }
    });
  }

  get hasBoundedContextNameValidationError(): boolean {
    return !BoundedContextName.isValid(this.inputBoundedContextName);
  }

  get boundedContextNameErrorMessage(): string {
    requirement(this.hasBoundedContextNameValidationError, 'this method must only call bounded context name has validation error');
    return BoundedContextName.validationErrorMessage;
  }

  get hasBoundedContextAliasValidationError(): boolean {
    return !BoundedContextAlias.isValid(this.inputBoundedContextAlias)
  }

  get boundedContextAliasErrorMessage(): string {
    requirement(this.hasBoundedContextAliasValidationError, 'this method must only call bounded context alias has validation error');
    return BoundedContextAlias.validationErrorMessage
  }

  get hasBoundedContextOverviewValidationError(): boolean {
    return !BoundedContextOverview.isValid(this.inputBoundedContextOverview);
  }

  get boundedContextOverviewErrorMessage(): string {
    requirement(this.hasBoundedContextOverviewValidationError, 'this method must only call bounded context overview has validation error');
    return BoundedContextOverview.validationErrorMessage
  }

  get canSubmit(): boolean {
    return BoundedContextAlias.isValid(this.inputBoundedContextAlias) &&
      BoundedContextName.isValid(this.inputBoundedContextName) &&
      BoundedContextOverview.isValid(this.inputBoundedContextOverview);
  }

  onSubmit(): void {
    requirement(this.canSubmit, 'submit button only can push all validation were passed.')
    const alias = new BoundedContextAlias(this.inputBoundedContextAlias);
    const name = new BoundedContextName(this.inputBoundedContextName);
    const overview = new BoundedContextOverview(this.inputBoundedContextOverview);

    const updatedBoundedContext = this.targetBoundedContext!
      .changeAlias(alias)
      .changeName(name)
      .changeOverview(overview);
    console.log('submit');

    lastValueFrom(this.boundedContextsService.update(updatedBoundedContext))
      .then(r => {
        this.router.navigateByUrl(`/bounded-contexts/${r.alias}`).then(_ => {});
      }
    );
  }
}

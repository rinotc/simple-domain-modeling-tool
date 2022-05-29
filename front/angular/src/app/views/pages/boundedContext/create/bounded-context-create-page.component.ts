import {Component, OnInit} from '@angular/core';
import {BoundedContextName} from "../../../../models/boundedContext/name/bounded-context-name";
import {requirement} from "../../../../dbc/dbc";
import {BoundedContextAlias} from "../../../../models/boundedContext/alias/bounded-context-alias";
import {BoundedContextOverview} from "../../../../models/boundedContext/overview/bounded-context-overview";
import {Router} from "@angular/router";
import {BoundedContextsService} from "../../../../models/boundedContext/state/bounded-contexts.service";

@Component({
  selector: 'app-project-create-page',
  templateUrl: './bounded-context-create-page.component.html',
  styleUrls: ['./bounded-context-create-page.component.scss']
})
export class BoundedContextCreatePageComponent implements OnInit {

  inputBoundedContextName: string = '';
  inputBoundedContextAlias: string = '';
  inputBoundedContextOverview: string = '';

  constructor(
    private router: Router,
    private boundedContextService: BoundedContextsService
  ) { }

  ngOnInit(): void {
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

    this.boundedContextService.create(alias, name, overview).forEach(_ => {
      this.router.navigateByUrl('/bounded-contexts').then((_) => {});
    });
  }
}

import {Component, OnInit} from '@angular/core';
import {BoundedContextName} from "../../../../models/boundedContext/name/bounded-context-name";
import {requirement} from "../../../../dbc/dbc";
import {BoundedContextAlias} from "../../../../models/boundedContext/alias/bounded-context-alias";
import {BoundedContextOverview} from "../../../../models/boundedContext/overview/bounded-context-overview";
import {BoundedContextRepository} from "../../../../models/boundedContext/bounded-context.repository";
import {Router} from "@angular/router";

@Component({
  selector: 'app-project-create-page',
  templateUrl: './project-create-page.component.html',
  styleUrls: ['./project-create-page.component.scss']
})
export class ProjectCreatePageComponent implements OnInit {

  inputProjectName: string = '';
  inputProjectAlias: string = '';
  inputProjectOverview: string = '';

  constructor(
    private router: Router,
    private projectRepository: BoundedContextRepository
  ) { }

  ngOnInit(): void {
  }

  get hasProjectNameValidationError(): boolean {
    return !BoundedContextName.isValid(this.inputProjectName);
  }

  get projectNameErrorMessage(): string {
    requirement(this.hasProjectNameValidationError, 'this method must only call project name has validation error');
    return BoundedContextName.validationErrorMessage;
  }

  get hasProjectAliasValidationError(): boolean {
    return !BoundedContextAlias.isValid(this.inputProjectAlias)
  }

  get projectAliasErrorMessage(): string {
    requirement(this.hasProjectAliasValidationError, 'this method must only call project alias has validation error');
    return BoundedContextAlias.validationErrorMessage
  }

  get hasProjectOverviewValidationError(): boolean {
    return !BoundedContextOverview.isValid(this.inputProjectOverview);
  }

  get projectOverviewErrorMessage(): string {
    requirement(this.hasProjectOverviewValidationError, 'this method must only call project overview has validation error');
    return BoundedContextOverview.validationErrorMessage
  }

  get canSubmit(): boolean {
    return BoundedContextAlias.isValid(this.inputProjectAlias) &&
      BoundedContextName.isValid(this.inputProjectName) &&
      BoundedContextOverview.isValid(this.inputProjectOverview);
  }

  onSubmit(): void {
    requirement(this.canSubmit, 'submit button only can all validation were passed.')
    const alias = new BoundedContextAlias(this.inputProjectAlias);
    const name = new BoundedContextName(this.inputProjectName);
    const overview = new BoundedContextOverview(this.inputProjectOverview);

    this.projectRepository.create(alias, name, overview).subscribe((_) => {
      this.router.navigateByUrl('/projects').then((_) => {});
    });
  }
}

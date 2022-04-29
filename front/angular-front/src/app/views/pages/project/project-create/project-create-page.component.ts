import {Component, OnInit} from '@angular/core';
import {ProjectName} from "../../../../models/project/name/project-name";
import {requirement} from "../../../../dbc/dbc";
import {ProjectAlias} from "../../../../models/project/alias/project-alias";
import {ProjectOverview} from "../../../../models/project/overview/project-overview";
import {ProjectRepository} from "../../../../models/project/project.repository";
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
    private projectRepository: ProjectRepository
  ) { }

  ngOnInit(): void {
  }

  get hasProjectNameValidationError(): boolean {
    return !ProjectName.isValid(this.inputProjectName);
  }

  get projectNameErrorMessage(): string {
    requirement(this.hasProjectNameValidationError, 'this method must only call project name has validation error');
    return ProjectName.validationErrorMessage;
  }

  get hasProjectAliasValidationError(): boolean {
    return !ProjectAlias.isValid(this.inputProjectAlias)
  }

  get projectAliasErrorMessage(): string {
    requirement(this.hasProjectAliasValidationError, 'this method must only call project alias has validation error');
    return ProjectAlias.validationErrorMessage
  }

  get hasProjectOverviewValidationError(): boolean {
    return !ProjectOverview.isValid(this.inputProjectOverview);
  }

  get projectOverviewErrorMessage(): string {
    requirement(this.hasProjectOverviewValidationError, 'this method must only call project overview has validation error');
    return ProjectOverview.validationErrorMessage
  }

  get canSubmit(): boolean {
    return ProjectAlias.isValid(this.inputProjectAlias) &&
      ProjectName.isValid(this.inputProjectName) &&
      ProjectOverview.isValid(this.inputProjectOverview);
  }

  onSubmit(): void {
    requirement(this.canSubmit, 'submit button only can all validation were passed.')
    const alias = new ProjectAlias(this.inputProjectAlias);
    const name = new ProjectName(this.inputProjectName);
    const overview = new ProjectOverview(this.inputProjectOverview);

    this.projectRepository.create(alias, name, overview).subscribe((_) => {
      this.router.navigateByUrl('/projects').then((_) => {});
    });
  }
}

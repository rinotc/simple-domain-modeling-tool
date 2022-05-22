import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";
import {UbiquitousName} from "../../../../models/domainModel/ubiquitousName/ubiquitous-name";
import {requirement} from "../../../../dbc/dbc";
import {EnglishName} from "../../../../models/domainModel/englishName/english-name";
import {Knowledge} from "../../../../models/domainModel/knowledge/knowledge";

@Component({
  selector: 'app-create-domain-model',
  templateUrl: './create-domain-model-page.component.html',
  styleUrls: ['./create-domain-model-page.component.scss']
})
export class CreateDomainModelPageComponent implements OnInit {

  inputUbiquitousName: string = '';
  inputEnglishName: string = '';
  inputKnowledge: string = '';

  constructor(
    router: Router
  ) { }

  ngOnInit(): void {
  }

  get hasUbiquitousNameValidationError(): boolean {
    return !UbiquitousName.isValid(this.inputUbiquitousName);
  }

  get ubiquitousNameValidationErrorMessage(): string {
    requirement(this.hasUbiquitousNameValidationError, 'this method must only call input ubiquitous name has validation error.');
    return UbiquitousName.validationErrorMessage;
  }

  get hasEnglishNameValidationError(): boolean {
    return !EnglishName.isValid(this.inputEnglishName);
  }

  get englishNameValidationErrorMessage(): string {
    requirement(this.hasEnglishNameValidationError, 'this method must only call input english name has validation error');
    return EnglishName.validationErrorMessage;
  }

  get canSubmit(): boolean {
    return UbiquitousName.isValid(this.inputUbiquitousName) && EnglishName.isValid(this.inputEnglishName);
  }

  onSubmit(): void {
    requirement(this.canSubmit, 'submit button only can push all validation were passed.');
    const ubiquitousName = new UbiquitousName(this.inputUbiquitousName);
    const englishName = new EnglishName(this.inputEnglishName);
    const knowledge = new Knowledge(this.inputKnowledge);

    console.log('submit');
  }
}

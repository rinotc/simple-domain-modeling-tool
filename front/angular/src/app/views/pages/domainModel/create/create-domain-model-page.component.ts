import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {UbiquitousName} from "../../../../models/domainModel/ubiquitousName/ubiquitous-name";
import {notNull, requirement} from "../../../../dbc/dbc";
import {EnglishName} from "../../../../models/domainModel/englishName/english-name";
import {Knowledge} from "../../../../models/domainModel/knowledge/knowledge";
import {CreateDomainModelInput} from "../../../../models/domainModel/state/io/create-domain-model-input";
import {DomainModelsService} from "../../../../models/domainModel/state/domain-models.service";
import {BoundedContextAlias} from "../../../../models/boundedContext/alias/bounded-context-alias";
import {BoundedContextsQuery} from "../../../../models/boundedContext/state/bounded-contexts.query";
import {BoundedContext} from "../../../../models/boundedContext/bounded-context";

@Component({
  selector: 'app-create-domain-model',
  templateUrl: './create-domain-model-page.component.html',
  styleUrls: ['./create-domain-model-page.component.scss']
})
export class CreateDomainModelPageComponent implements OnInit {

  inputUbiquitousName: string = '';
  inputEnglishName: string = '';
  inputKnowledge: string = '';

  private _boundedContext!: BoundedContext;

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private boundedContextsQuery: BoundedContextsQuery,
    private domainModelsService: DomainModelsService
  ) {
    const aliasString = this.route.snapshot.paramMap.get('boundedContextAlias');
    notNull(aliasString, `alias string must ot be null.`);
    const alias = new BoundedContextAlias(aliasString!);
    this.boundedContextsQuery.contexts$.subscribe(contexts => {
      const context = contexts.findByAlias(alias);
      if (context === undefined) {
        this.router.navigateByUrl('404').then(_ => {});
      } else {
        this._boundedContext = context;
      }
    })
  }
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

    const input: CreateDomainModelInput = {
      boundedContextId: this._boundedContext.id,
      ubiquitousName,
      englishName,
      knowledge
    }

    this.domainModelsService.create(input).forEach(_ => {
      this.router.navigateByUrl(`bounded-contexts/${this._boundedContext.alias.value}`).then(_ => {});
    })
  }
}

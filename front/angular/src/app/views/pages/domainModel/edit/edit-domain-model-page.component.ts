import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { DomainModelsService } from '../../../../models/domainModel/state/domain-models.service';
import { notNull, requirement } from '../../../../dbc/dbc';
import { EnglishName } from '../../../../models/domainModel/englishName/english-name';
import { BoundedContextAlias } from '../../../../models/boundedContext/alias/bounded-context-alias';
import { BoundedContextsService } from '../../../../models/boundedContext/state/bounded-contexts.service';
import { BoundedContextsQuery } from '../../../../models/boundedContext/state/bounded-contexts.query';
import { DomainModel } from '../../../../models/domainModel/domain-model';
import { DomainModelsQuery } from '../../../../models/domainModel/state/domain-models.query';
import { redirectTo404 } from '../../../../helper/routing-helper';
import { UbiquitousName } from '../../../../models/domainModel/ubiquitousName/ubiquitous-name';
import { Knowledge } from '../../../../models/domainModel/knowledge/knowledge';
import { BoundedContext } from '../../../../models/boundedContext/bounded-context';

@Component({
  selector: 'app-edit-domain-model-page',
  templateUrl: './edit-domain-model-page.component.html',
  styleUrls: ['./edit-domain-model-page.component.scss'],
})
export class EditDomainModelPageComponent implements OnInit {
  @Input() domainModel: DomainModel | undefined;
  private boundedContext: BoundedContext | undefined;

  inputUbiquitousName: string = '';
  inputEnglishName: string = '';
  inputKnowledge: string = '';

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private boundedContextsService: BoundedContextsService,
    private boundedContextsQuery: BoundedContextsQuery,
    private domainModelService: DomainModelsService,
    private domainModelsQuery: DomainModelsQuery
  ) {}

  async ngOnInit(): Promise<void> {
    if (this.domainModel == undefined) {
      const aliasStr = this.route.snapshot.paramMap.get('boundedContextAlias');
      const englishNameStr = this.route.snapshot.paramMap.get('englishName');
      notNull(aliasStr, `BoundedContextAlias string must no be null.`);
      notNull(englishNameStr, `englishName string must no be null.`);
      const alias = new BoundedContextAlias(aliasStr!);
      const englishName = new EnglishName(englishNameStr!);
      await this.boundedContextsService.fetchByIdOrAlias(alias);
      const contexts = await this.boundedContextsQuery.contexts;
      const context = contexts.findByAlias(alias);
      if (context) {
        this.boundedContext = context;
        await this.domainModelService.fetchByIdOrEnglishName(
          englishName,
          alias
        );
        const domainModels = await this.domainModelsQuery.models;
        const dModel = domainModels.findByEnglishName(englishName, context.id);
        if (dModel) {
          this.domainModel = dModel;
        } else {
          redirectTo404(this.router);
        }
      } else {
        redirectTo404(this.router);
      }
    }
    this.inputUbiquitousName = this.domainModel!.ubiquitousName.value;
    this.inputEnglishName = this.domainModel!.englishName.value;
    this.inputKnowledge = this.domainModel!.knowledge.value;
  }

  get isLoading(): boolean {
    return !this.domainModel;
  }

  get hasUbiquitousNameValidationError(): boolean {
    return UbiquitousName.isValid(this.inputUbiquitousName);
  }

  get ubiquitousNameValidationErrorMessage(): string {
    requirement(
      this.hasUbiquitousNameValidationError,
      'this method must only call input ubiquitous name has validation error.'
    );
    return UbiquitousName.validationErrorMessage;
  }

  get hasEnglishNameValidationError(): boolean {
    return !EnglishName.isValid(this.inputEnglishName);
  }

  get englishNameValidationErrorMessage(): string {
    requirement(
      this.hasEnglishNameValidationError,
      'this method must only call input english name has validation error'
    );
    return EnglishName.validationErrorMessage;
  }

  get canSubmit(): boolean {
    return (
      UbiquitousName.isValid(this.inputUbiquitousName) &&
      EnglishName.isValid(this.inputEnglishName)
    );
  }

  onSubmit(): void {
    requirement(
      this.canSubmit,
      'submit button only can push all validation were passed.'
    );

    const ubiquitousName = new UbiquitousName(this.inputUbiquitousName);
    const englishName = new EnglishName(this.inputEnglishName);
    const knowledge = new Knowledge(this.inputKnowledge);

    const updatedDomainModel = this.domainModel!.changeUbiquitousName(
      ubiquitousName
    )
      .changeEnglishName(englishName)
      .changeKnowledge(knowledge);

    this.domainModelService.update(updatedDomainModel).forEach((_) => {
      this.router
        .navigateByUrl(
          `/bounded-contexts/${
            this.boundedContext!.alias.value
          }/domain-models/${englishName.value}`
        )
        .then((_) => {});
    });
  }
}

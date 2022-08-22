import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { DomainModelsService } from '../../../../../models/domainModel/state/domain-models.service';
import { DomainModelsQuery } from '../../../../../models/domainModel/state/domain-models.query';
import { DomainModel } from '../../../../../models/domainModel/domain-model';
import { notNull, requirement } from '../../../../../dbc/dbc';
import { EnglishName } from '../../../../../models/domainModel/englishName/english-name';
import { BoundedContextsQuery } from '../../../../../models/boundedContext/state/bounded-contexts.query';
import { BoundedContextAlias } from '../../../../../models/boundedContext/alias/bounded-context-alias';
import { redirectTo404 } from '../../../../../helper/routing-helper';
import { BoundedContextsService } from '../../../../../models/boundedContext/state/bounded-contexts.service';

@Component({
  selector: 'app-domain-model-detail-page',
  templateUrl: './domain-model-detail-page.component.html',
  styleUrls: ['./domain-model-detail-page.component.scss'],
})
export class DomainModelDetailPageComponent implements OnInit {
  private _domainModel: DomainModel | null = null;

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private domainModelService: DomainModelsService,
    private domainModelQuery: DomainModelsQuery,
    private boundedContextQuery: BoundedContextsQuery,
    private boundedContextService: BoundedContextsService
  ) {}

  async ngOnInit(): Promise<void> {
    const englishNameString = this.route.snapshot.paramMap.get('englishName');
    const aliasString = this.route.snapshot.paramMap.get('boundedContextAlias');
    notNull(englishNameString, `englishName string must not be null.`);
    notNull(aliasString, `BoundedContextAlias string must not be null`);
    const englishName = new EnglishName(englishNameString!);
    const alias = new BoundedContextAlias(aliasString!);
    await this.boundedContextService.fetchByIdOrAlias(alias);
    const contexts = await this.boundedContextQuery.contexts;
    const context = contexts.findByAlias(alias);
    if (context) {
      await this.domainModelService.fetchByIdOrEnglishName(englishName, alias);
      const domainModels = await this.domainModelQuery.models;
      console.log(domainModels);
      const domainModel = domainModels.findByEnglishName(
        englishName,
        context.id
      );
      if (domainModel) {
        this._domainModel = domainModel;
      } else {
        redirectTo404(this.router);
      }
    } else {
      redirectTo404(this.router);
    }
  }

  get isLoading(): boolean {
    return this._domainModel == null;
  }

  get domainModel(): DomainModel {
    requirement(!this.isLoading, 'assume domain model defined.');
    return this._domainModel!;
  }
}

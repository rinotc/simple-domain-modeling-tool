import { Component, Input, OnInit } from '@angular/core';
import { DomainModel } from '../../../../../models/domainModel/domain-model';
import { BoundedContext } from '../../../../../models/boundedContext/bounded-context';
import { DomainModelsService } from '../../../../../models/domainModel/state/domain-models.service';
import { DomainModelsQuery } from '../../../../../models/domainModel/state/domain-models.query';

@Component({
  selector: 'app-list-domain-models',
  templateUrl: './list-domain-models.component.html',
  styleUrls: ['./list-domain-models.component.scss'],
})
export class ListDomainModelsComponent implements OnInit {
  @Input() boundedContext!: BoundedContext;

  domainModels: DomainModel[] = [];

  displayedColumns = ['ubiquitous-name', 'english-name', 'knowledge'];

  constructor(
    private domainModelsService: DomainModelsService,
    private domainModelsQuery: DomainModelsQuery
  ) {
    this.domainModelsQuery.models$.subscribe((dms) => {
      this.domainModels = dms.models;
    });
  }

  ngOnInit(): void {
    this.domainModelsService.fetchAll(this.boundedContext.id);
  }

  ubiquitousName(dm: DomainModel): string {
    return dm.ubiquitousName.value;
  }

  englishName(dm: DomainModel): string {
    return dm.englishName.value;
  }

  knowledge(dm: DomainModel): string {
    return dm.knowledge.value;
  }
}

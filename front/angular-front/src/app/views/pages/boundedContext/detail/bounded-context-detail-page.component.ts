import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {HeaderService} from "../../../../store/title/header.service";
import {notNull, requirement} from "../../../../dbc/dbc";
import {BoundedContextAlias} from "../../../../models/boundedContext/alias/bounded-context-alias";
import {BoundedContextRepository} from "../../../../models/boundedContext/bounded-context.repository";
import {BoundedContext} from "../../../../models/boundedContext/bounded-context";

@Component({
  selector: 'app-project-detail',
  templateUrl: './bounded-context-detail-page.component.html',
  styleUrls: ['./bounded-context-detail-page.component.scss']
})
export class BoundedContextDetailPageComponent implements OnInit {

  private _boundedContext: BoundedContext | null = null;

  constructor(
    private route: ActivatedRoute,
    private headerService: HeaderService,
    private boundedContextRepository: BoundedContextRepository
  ) {}

  ngOnInit(): void {
    const aliasString = this.route.snapshot.paramMap.get('boundedContextAlias');
    notNull(aliasString, `alias string must not be null but ${aliasString}`);
    const alias = new BoundedContextAlias(aliasString!);
    this.boundedContextRepository.findBy(alias).subscribe(context => {
      this._boundedContext = context;
      this.headerService.update(context.name.value);
    });
  }

  get isLoading(): boolean {
    return this._boundedContext == null;
  }

  get boundedContext(): BoundedContext {
    requirement(!this.isLoading, 'assume this method call after loading.');
    return this._boundedContext!;
  }
}

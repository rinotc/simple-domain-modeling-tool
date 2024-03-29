import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HeaderService } from '../../../../store/title/header.service';
import { notNull, requirement } from '../../../../dbc/dbc';
import { BoundedContextAlias } from '../../../../models/boundedContext/alias/bounded-context-alias';
import { BoundedContext } from '../../../../models/boundedContext/bounded-context';
import { BoundedContextsQuery } from '../../../../models/boundedContext/state/bounded-contexts.query';
import { BoundedContextsService } from '../../../../models/boundedContext/state/bounded-contexts.service';
import { redirectTo404 } from '../../../../helper/routing-helper';
import { MatDialog } from '@angular/material/dialog';
import { DeleteBoundedContextDialogComponent } from './delete-bounded-context-dialog/delete-bounded-context-dialog.component';

@Component({
  selector: 'app-project-detail',
  templateUrl: './bounded-context-detail-page.component.html',
  styleUrls: ['./bounded-context-detail-page.component.scss'],
})
export class BoundedContextDetailPageComponent implements OnInit {
  private _boundedContext: BoundedContext | null = null;

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private headerService: HeaderService,
    private boundedContextsService: BoundedContextsService,
    private boundedContextsQuery: BoundedContextsQuery,
    public dialog: MatDialog
  ) {}

  async ngOnInit(): Promise<void> {
    const aliasString = this.route.snapshot.paramMap.get('boundedContextAlias');
    notNull(aliasString, `alias string must not be null but ${aliasString}`);
    const alias = new BoundedContextAlias(aliasString!);
    await this.boundedContextsService.fetchByIdOrAlias(alias);
    const contexts = await this.boundedContextsQuery.contexts;
    const context = contexts.findByAlias(alias);
    if (context === undefined) {
      redirectTo404(this.router);
    } else {
      this.headerService.update(context.name.value);
      this._boundedContext = context;
    }
  }

  get isLoading(): boolean {
    return this._boundedContext == null;
  }

  get boundedContext(): BoundedContext {
    requirement(!this.isLoading, 'assume this method call after loading.');
    return this._boundedContext!;
  }

  clickCreateDomainModelButton(): void {
    this.router
      .navigateByUrl(
        `bounded-contexts/${this.boundedContext.alias.value}/domain-models/create`
      )
      .then((isSuccess) => {
        if (isSuccess) {
          console.log('success!');
        } else {
          console.log('failed!');
        }
      });
  }

  clickEditButton(): void {
    this.router
      .navigateByUrl(`bounded-contexts/${this.boundedContext.alias.value}/edit`)
      .then((isSuccess) => {
        if (isSuccess) {
          console.log('success!');
        } else {
          console.log('failed!');
        }
      });
  }

  clickDeleteButton(): void {
    console.log('click delete button');
    this.openDialog();
  }

  private openDialog() {
    const dialogRef = this.dialog.open(DeleteBoundedContextDialogComponent, {
      width: '50vw',
      height: '50vh',
      data: {
        boundedContextName: this.boundedContext.name.value,
        boundedContextAlias: this.boundedContext.alias.value,
      },
    });
    dialogRef.afterClosed().subscribe(async (wantDelete) => {
      if (wantDelete) {
        await this.boundedContextsService
          .delete(this.boundedContext.id)
          .then((_) =>
            this.router.navigateByUrl('/bounded-contexts').then((_) => {})
          );
      }
    });
  }
}

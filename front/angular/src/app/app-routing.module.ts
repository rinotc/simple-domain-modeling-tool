import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BoundedContextsListPageComponent } from './views/pages/boundedContext/list/bounded-contexts-list-page.component';
import { BoundedContextDetailPageComponent } from './views/pages/boundedContext/detail/bounded-context-detail-page.component';
import { BoundedContextCreatePageComponent } from './views/pages/boundedContext/create/bounded-context-create-page.component';
import { CreateDomainModelPageComponent } from './views/pages/domainModel/create/create-domain-model-page.component';
import { NotFoundComponent } from './views/pages/error/not-found/not-found.component';
import { BoundedContextUpdatePageComponent } from './views/pages/boundedContext/update/bounded-context-update-page.component';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'bounded-contexts',
    pathMatch: 'full',
  },
  {
    path: '404',
    component: NotFoundComponent,
  },
  {
    path: 'bounded-contexts',
    children: [
      {
        path: '',
        component: BoundedContextsListPageComponent,
      },
      {
        path: 'create',
        component: BoundedContextCreatePageComponent,
      },
      {
        path: ':boundedContextAlias',
        children: [
          {
            path: '',
            redirectTo: 'domain-models',
            pathMatch: 'full',
          },
          {
            path: 'domain-models',
            component: BoundedContextDetailPageComponent,
          },
          {
            path: 'edit',
            component: BoundedContextUpdatePageComponent,
          },
          {
            path: 'domain-models/create',
            component: CreateDomainModelPageComponent,
          },
        ],
      },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}

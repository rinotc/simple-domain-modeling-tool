import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {BoundedContextsListPageComponent} from "./views/pages/boundedContext/list/bounded-contexts-list-page.component";
import {BoundedContextDetailPageComponent} from "./views/pages/boundedContext/detail/bounded-context-detail-page.component";
import {BoundedContextCreatePageComponent} from "./views/pages/boundedContext/create/bounded-context-create-page.component";
import {CreateDomainModelPageComponent} from "./views/pages/domainModel/create/create-domain-model-page.component";
import {NotFoundComponent} from "./views/pages/error/not-found/not-found.component";

const routes: Routes = [
  {
    path: '',
    redirectTo: 'bounded-contexts',
    pathMatch: 'full'
  },
  {
    path: '404',
    component: NotFoundComponent,
  },
  {
    path: 'bounded-contexts',
    component: BoundedContextsListPageComponent
  },
  {
    path: 'bounded-contexts/create',
    component: BoundedContextCreatePageComponent
  },
  {
    path: 'bounded-contexts/:boundedContextAlias',
    component: BoundedContextDetailPageComponent
  },
  {
    path: 'bounded-contexts/:boundedContextAlias/domain-models/create',
    component: CreateDomainModelPageComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

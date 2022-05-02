import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {BoundedContextsListPageComponent} from "./views/pages/boundedContext/list/bounded-contexts-list-page.component";
import {BoundedContextDetailPageComponent} from "./views/pages/boundedContext/detail/bounded-context-detail-page.component";
import {BoundedContextCreatePageComponent} from "./views/pages/boundedContext/create/bounded-context-create-page.component";

const routes: Routes = [
  {
    path: '',
    redirectTo: 'bounded-contexts',
    pathMatch: 'full'
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
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

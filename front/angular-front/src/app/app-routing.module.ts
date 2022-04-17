import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ProjectListPageComponent} from "./views/pages/project/project-list-page/project-list-page.component";

const routes: Routes = [
  {
    path: 'projects',
    component: ProjectListPageComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

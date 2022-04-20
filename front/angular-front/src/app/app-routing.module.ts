import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ProjectListPageComponent} from "./views/pages/project/project-list-page/project-list-page.component";
import {ProjectDetailComponent} from "./views/components/project/project-detail/project-detail.component";
import {ProjectCreateComponent} from "./views/components/project/project-create/project-create.component";

const routes: Routes = [
  {
    path: 'projects',
    component: ProjectListPageComponent
  },
  {
    path: 'projects/create',
    component: ProjectCreateComponent
  },
  {
    path: 'projects/:projectId',
    component: ProjectDetailComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

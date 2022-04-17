import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HttpClientModule} from "@angular/common/http";
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatToolbarModule} from "@angular/material/toolbar";
import {HeaderComponent} from './views/components/header/header/header.component';
import {MatIconModule} from "@angular/material/icon";
import {MatButtonModule} from "@angular/material/button";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {AkitaNgDevtools} from '@datorama/akita-ngdevtools';
import {environment} from '../environments/environment';
import {ProjectListPageComponent} from './views/pages/project/project-list-page/project-list-page.component';
import {MatCardModule} from "@angular/material/card";
import {
  ProjectListComponentComponent
} from './views/components/project/project-list-component/project-list-component.component';
import {MatTableModule} from "@angular/material/table";
import { WelcomeComponent } from './views/components/welcome/welcome.component';
import { ProjectDetailComponent } from './views/components/project/project-detail/project-detail.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    ProjectListPageComponent,
    ProjectListComponentComponent,
    WelcomeComponent,
    ProjectDetailComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MatToolbarModule,
    MatIconModule,
    MatButtonModule,
    FormsModule,
    ReactiveFormsModule,
    environment.production ? [] : AkitaNgDevtools.forRoot(),
    MatCardModule,
    MatTableModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }

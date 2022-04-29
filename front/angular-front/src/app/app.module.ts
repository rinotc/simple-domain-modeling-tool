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
import {FlexLayoutModule} from "@angular/flex-layout";
import { ProjectCreatePageComponent } from './views/pages/project/project-create/project-create-page.component';
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    ProjectListPageComponent,
    ProjectListComponentComponent,
    WelcomeComponent,
    ProjectDetailComponent,
    ProjectCreatePageComponent,
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
    MatTableModule,
    FlexLayoutModule,
    MatFormFieldModule,
    MatInputModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }

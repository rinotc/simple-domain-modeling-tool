import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HttpClientModule} from "@angular/common/http";
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { UserListItemComponent } from './views/components/user-list-item/user-list-item.component';
import { UserListComponent } from './views/components/user-list/user-list.component';
import {MatToolbarModule} from "@angular/material/toolbar";
import { HeaderComponent } from './views/components/header/header/header.component';
import {MatIconModule} from "@angular/material/icon";
import {MatButtonModule} from "@angular/material/button";
import { UserListFilterComponentComponent } from './views/components/user-list-filter-component/user-list-filter-component.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";

@NgModule({
  declarations: [
    AppComponent,
    UserListItemComponent,
    UserListComponent,
    HeaderComponent,
    UserListFilterComponentComponent
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
    ReactiveFormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }

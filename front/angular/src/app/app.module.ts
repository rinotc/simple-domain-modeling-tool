import { ErrorHandler, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatToolbarModule } from '@angular/material/toolbar';
import { HeaderComponent } from './views/components/header/header/header.component';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AkitaNgDevtools } from '@datorama/akita-ngdevtools';
import { environment } from '../environments/environment';
import { BoundedContextsListPageComponent } from './views/pages/boundedContext/list/bounded-contexts-list-page.component';
import { MatCardModule } from '@angular/material/card';
import { BoundedContextListComponentComponent } from './views/components/boundedContext/bounded-context-list-component/bounded-context-list-component.component';
import { MatTableModule } from '@angular/material/table';
import { WelcomeComponent } from './views/components/welcome/welcome.component';
import { BoundedContextDetailPageComponent } from './views/pages/boundedContext/detail/bounded-context-detail-page.component';
import { FlexLayoutModule } from '@angular/flex-layout';
import { BoundedContextCreatePageComponent } from './views/pages/boundedContext/create/bounded-context-create-page.component';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatMenuModule } from '@angular/material/menu';
import { LayoutModule } from '@angular/cdk/layout';
import { ListDomainModelsComponent } from './views/pages/boundedContext/detail/list-domain-models/list-domain-models.component';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSortModule } from '@angular/material/sort';
import { CreateDomainModelPageComponent } from './views/pages/domainModel/create/create-domain-model-page.component';
import { NotFoundComponent } from './views/pages/error/not-found/not-found.component';
import { BoundedContextUpdatePageComponent } from './views/pages/boundedContext/update/bounded-context-update-page.component';
import { DeleteBoundedContextDialogComponent } from './views/pages/boundedContext/detail/delete-bounded-context-dialog/delete-bounded-context-dialog.component';
import { MatDialogModule } from '@angular/material/dialog';
import { DomainModelDetailPageComponent } from './views/pages/domainModel/detail/domain-model-detail-page/domain-model-detail-page.component';
import { MatListModule } from '@angular/material/list';
import { EditDomainModelPageComponent } from './views/pages/domainModel/edit/edit-domain-model-page.component';
import { LoginPageComponent } from './views/pages/login/login-page/login-page.component';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { DefaultErrorHandler } from './system/default-error-handler';
import { DefaultInterceptor } from './system/default-interceptor';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    BoundedContextsListPageComponent,
    BoundedContextListComponentComponent,
    WelcomeComponent,
    BoundedContextDetailPageComponent,
    BoundedContextCreatePageComponent,
    ListDomainModelsComponent,
    CreateDomainModelPageComponent,
    NotFoundComponent,
    BoundedContextUpdatePageComponent,
    DeleteBoundedContextDialogComponent,
    DomainModelDetailPageComponent,
    EditDomainModelPageComponent,
    LoginPageComponent,
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
    MatInputModule,
    MatProgressSpinnerModule,
    MatProgressBarModule,
    MatGridListModule,
    MatMenuModule,
    LayoutModule,
    MatPaginatorModule,
    MatSortModule,
    MatDialogModule,
    MatListModule,
    MatSnackBarModule,
  ],
  providers: [
    { provide: ErrorHandler, useClass: DefaultErrorHandler },
    { provide: HTTP_INTERCEPTORS, useClass: DefaultInterceptor, multi: true },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}

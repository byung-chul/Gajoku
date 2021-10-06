import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS} from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AuthGuard } from './auth.guard';

import { UtilService } from './util.service';
import { AuthService } from './auth.service';
import { RequestInterceptor } from './request-interceptor.service';
import { UserService } from './user.service';
import { DataService} from './services/data.service';

import { AppComponent } from './app.component';
import { WelcomeComponent } from './welcome/welcome.component';
import { Error404Component } from './error404/error404.component';
import { LoginComponent } from './login/login.component';
import { UserNewComponent } from './user-new/user-new.component';
import { UserIndexComponent } from './user-index/user-index.component';
import { UserMenuComponent } from './user-menu/user-menu.component';
import { UserRecognizeComponent } from './user-recognize/user-recognize.component';

import { AddDialogComponent} from './dialogs/add/add.dialog.component';
import { DeleteDialogComponent} from './dialogs/delete/delete.dialog.component';
import {EditDialogComponent} from './dialogs/edit/edit.dialog.component';

import { MaterialModule } from './material.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import {CdkTableModule} from '@angular/cdk/table';

@NgModule({
  declarations: [
    AppComponent,
    WelcomeComponent,
    Error404Component,
    LoginComponent,
    UserNewComponent,
    UserIndexComponent,
    UserMenuComponent,
    UserRecognizeComponent,
    AddDialogComponent,
    DeleteDialogComponent,
    EditDialogComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    BrowserAnimationsModule, // BrowserAnimationsModule 추가
    MaterialModule,
    CdkTableModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: RequestInterceptor,
      multi: true,
    },
    AuthGuard,
    UtilService,
    AuthService,
    UserService,
    DataService,
  ],
  entryComponents: [
    AddDialogComponent,
    DeleteDialogComponent,
    EditDialogComponent,
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }

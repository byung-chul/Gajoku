import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { AuthGuard } from './auth.guard';

import { WelcomeComponent } from './welcome/welcome.component';
import { Error404Component } from './error404/error404.component';
import { LoginComponent } from './login/login.component';
import { UserNewComponent } from './user-new/user-new.component';
import { UserIndexComponent } from './user-index/user-index.component';
import { UserMenuComponent} from './user-menu/user-menu.component';
import { UserRecognizeComponent} from './user-recognize/user-recognize.component';

const routes: Routes = [
  { path: '',  component: WelcomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'new',  component: UserNewComponent },
  { path: '', canActivate: [AuthGuard],
    children: [
      { path: 'users', component: UserIndexComponent },
      { path: 'menu', component: UserMenuComponent },
      { path: 'recognize', component: UserRecognizeComponent}
    ]
  },
  { path: '**', component: Error404Component },
];

@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {}

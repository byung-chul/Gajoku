import { environment } from '../environments/environment';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

import 'rxjs/add/operator/toPromise';

import { UtilService } from './util.service';
import { ApiResponse } from './api-response';
import { User } from './user';

@Injectable()
export class AuthService {
  private apiBaseUrl = `${environment.apiBaseUrl}`;

  constructor(
    private http: HttpClient,
    private router: Router,
    private utilService: UtilService,
  ) { }
/*
  login(username: string, password: string): Promise<any> {
    return this.http.post<ApiResponse>(`${this.apiBaseUrl}/login`, {id: username, password: password})
              .toPromise()
              .then(this.utilService.checkSuccess)
              .then(response => {localStorage.setItem('token', response.data);
              })
              .catch(this.utilService.handleApiError);
  }
*/
  login(id: string, password: string): Promise<any> {
    return this.http.get(`${this.apiBaseUrl}/r_login?r_admin=` + id + '&r_password=' + password)
      .toPromise()
      .then(this.utilService.checkSuccess)
      .then(response => {
//        console.log('save to token response : ', response[0]['restaurantAdmin']);
        localStorage.setItem('token', response[0]['restaurantAdmin']);
        localStorage.setItem('restaurantId', response[0]['restaurantId']);
//        localStorage.setItem('token', response[0]['id']);
//      .then(response => {localStorage.setItem('token', response.data);
      })
      .catch(this.utilService.handleApiError);
  }

  me(): Promise<User> {
    return this.http.get<ApiResponse>(`${this.apiBaseUrl}/me`)
              .toPromise()
              .then(this.utilService.checkSuccess)
              .then(response => {localStorage.setItem('currentUser', JSON.stringify(response.data));
                return response.data as User
              })
              .catch(response =>{
                this.logout();
                return this.utilService.handleApiError(response);
              });
  }

  refresh(): Promise<any> {
    console.log('in refresh');
    return this.http.get(`${this.apiBaseUrl}`)
              .toPromise()
              .then(this.utilService.checkSuccess)
              .then(response => {
                console.log('in refresh response');
                localStorage.setItem('token', response.data);
                if (!this.getCurrentUser()) { return this.me(); }
              })
              .catch(response =>{
                console.log('out refresh response');
//                this.logout();
                return this.utilService.handleApiError(response);
              });
  }


  getToken(): string {
    return localStorage.getItem('token');
  }

  getCurrentUser(): boolean {
    if (localStorage.getItem('token')) {
      return true;
    } else {
      return false;
    }
  }



// 이거 수정해야함
  isLoggedIn(): boolean {
    var token = localStorage.getItem('token');
//    console.log('현재 로그인 되어있는 id는 : ', token);
    if( token !== null) return true;
    else return false;
  }

  logout(): void {
    console.log('in logout');
    localStorage.removeItem('restaurantId');
    localStorage.removeItem('token');
//    localStorage.removeItem('currentUser');
//    localStorage.clear();
    this.router.navigate(['/']);
  }
}

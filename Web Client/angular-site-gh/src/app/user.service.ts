import { environment } from '../environments/environment';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import 'rxjs/add/operator/toPromise';

import { UtilService } from './util.service';
import { ApiResponse } from './api-response';
import { User } from './user';

@Injectable()
export class UserService {
  private apiBaseUrl = `${environment.apiBaseUrl}`;

  constructor(
    private http: HttpClient,
    private utilService: UtilService,
  ) { }

  index(): Promise<User[]> {
    return this.http.get<ApiResponse>(`${this.apiBaseUrl}`)
              .toPromise()
              .then(this.utilService.checkSuccess)
              .then(response => {
                return response.data as User[]
              })
              .catch(this.utilService.handleApiError);
  }

  show(username: string): Promise<User> {
    return this.http.get<ApiResponse>(`${this.apiBaseUrl}/${username}`)
              .toPromise()
              .then(this.utilService.checkSuccess)
              .then(response => {
                return response.data as User
              })
              .catch(this.utilService.handleApiError);
  }
/*
  create(user: User): Promise<User> {
    console.log(user);
    return this.http.post(`${this.apiBaseUrl}/r_signup`, user)
              .toPromise()
              .then(this.utilService.checkSuccess)
              .then(response => {
                return response.data as User
              })
              .catch(this.utilService.handleApiError);
  }
*/
// 전화번호(number)는 안적어도됨
  create(r_admin : string, r_password: string, r_type : string, r_name : string, r_number : string, r_address : string, r_detail : string): Promise<any> {
    return this.http.post(`${this.apiBaseUrl}/r_login`, {r_admin: r_admin, r_password: r_password, r_type: r_type, r_name: r_name, r_number: r_number, r_address: r_address, r_detail: r_detail}, {responseType: 'text'})
      .toPromise()
      .then(this.utilService.checkSuccess)
      .then(response => {
        console.log(response);
      })
      .catch(this.utilService.handleApiError);
  }

  menu(): Promise<any> {
    return this.http.get(`${this.apiBaseUrl}/restaurant?u_address=경기도 수원시 영통구 원천동 월드컵로 206&r_type=한식음식점`)
      .toPromise()
      .then(this.utilService.checkSuccess)
      .then(response => {
        console.log(response);
      })
      .catch(this.utilService.handleApiError);
  }



  update(username: string, user: User): Promise<User> {
    return this.http.put<ApiResponse>(`${this.apiBaseUrl}/${username}`, user)
              .toPromise()
              .then(this.utilService.checkSuccess)
              .then(response => {
                return response.data as User
              })
              .catch(this.utilService.handleApiError);
  }

  destroy(username: string): Promise<User> {
    return this.http.delete<ApiResponse>(`${this.apiBaseUrl}/${username}`)
              .toPromise()
              .then(this.utilService.checkSuccess)
              .then(response => {
                return response.data as User
              })
              .catch(this.utilService.handleApiError);
  }
}

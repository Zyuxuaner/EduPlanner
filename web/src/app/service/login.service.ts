import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable, tap} from "rxjs";
import {ResponseBody} from "../entity/response-body";

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  constructor(private httpClient: HttpClient) {}

  login(user: {username: string, password: string}): Observable<ResponseBody> {
    const url = '/login';
    return this.httpClient.post<ResponseBody>(url, user)
      .pipe(tap( response => {
        if (response.data) {
          const user = {
            username: response.data.username,
            password: response.data.password
          };
        }
      }));
  }

  logout(): Observable<ResponseBody> {
    const url = '/logout';
    return this.httpClient.get<ResponseBody>(url);
  }
}

import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {ResponseBody} from "../entity/response-body";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class PersonService {

  constructor(private http: HttpClient) { }
  private baseUrl = 'http://localhost:8080/User';

  // 修改密码
  changePassword(params: HttpParams): Observable<ResponseBody> {
    return this.http.get<ResponseBody>(`${this.baseUrl}/changePassword`, { params });
  }
}

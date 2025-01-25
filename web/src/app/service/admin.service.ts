import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {BehaviorSubject, Observable, Subject, tap} from "rxjs";
import {Admin} from "../entity/admin";
import {ResponseBody} from "../entity/response-body";

@Injectable({
  providedIn: 'root'
})
export class AdminService {
  private baseUrl = 'http://localhost:8080/Admin';

  constructor(private httpClient: HttpClient) { }

  add(admin: {name: string, username: string, ano: string, role: number}): Observable<ResponseBody> {
    return this.httpClient.post<ResponseBody>(`${this.baseUrl}/add`, admin);
  }

  getAll(): Observable<Admin[]> {
    return this.httpClient.get<Admin[]>(`${this.baseUrl}/getAll`);
    }
}

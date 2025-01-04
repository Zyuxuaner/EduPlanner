import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {BehaviorSubject, Observable, Subject, tap} from "rxjs";
import {Admin} from "../entity/admin";

@Injectable({
  providedIn: 'root'
})
export class AdminService {
  private baseUrl = '/admin'

  constructor(private httpClient: HttpClient) { }

  add(admin: {name: string, username: string, ano: string, role: number}): Observable<Admin> {
    return this.httpClient.post<Admin>(`${this.baseUrl}/add`, admin);
  }

  getAll(): Observable<Admin[]> {
    return this.httpClient.get<Admin[]>(`${this.baseUrl}/getAll`);
    }
}

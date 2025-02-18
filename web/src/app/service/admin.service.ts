import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
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

  delete(id: number): Observable<ResponseBody> {
    return this.httpClient.delete<ResponseBody>(`${this.baseUrl}/delete/${id}`);
  }

  resetPassword(id: number, newPassword: string): Observable<ResponseBody> {
    return this.httpClient.patch<ResponseBody>(`${this.baseUrl}/resetPassword/${id}`, newPassword);
  }

  searchAdmins(name: string, ano: string): Observable<Admin[]> {
    const params = { name, ano };
    return this.httpClient.get<Admin[]>(`${this.baseUrl}/search`, { params });
  }

  getAdminById(id: number): Observable<Admin> {
    return this.httpClient.get<Admin>(`${this.baseUrl}/getAdminById/${id}`);
  }

  updateAdmin(admin: any): Observable<ResponseBody> {
    return this.httpClient.patch<ResponseBody>(`${this.baseUrl}/update/${admin.id}`, admin);
  }
}

import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {School} from "../entity/school";
import {ResponseBody} from "../entity/response-body";

@Injectable({
  providedIn: 'root'
})
export class SchoolService {
  private url = 'http://localhost:8080/School';

  constructor(private httpClient: HttpClient) { }

  add(school: {name: string | null}): Observable<ResponseBody> {
    return this.httpClient.post<ResponseBody>(`${this.url}/add`, school);
  }

  deleteSchool(id: number): Observable<ResponseBody> {
    return this.httpClient.delete<ResponseBody>(`${this.url}/delete/${id}`);
  }

  getAll(): Observable<School[]> {
    return this.httpClient.get<School[]>(`${this.url}/getAll`);
  }

  getSchoolById(id: number): Observable<School[]> {
    return this.httpClient.get<School[]>(`${this.url}/${id}`);
  }

  searchSchools(searchName: string): Observable<School[]> {
    return this.httpClient.get<School[]>(`${this.url}/search?name=${searchName}`);
  }

  updateSchool(id: number, name: string): Observable<ResponseBody> {
    return this.httpClient.put<ResponseBody>(`${this.url}/${id}`, name);
  }
}

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
  getAll(): Observable<School[]> {
    return this.httpClient.get<School[]>(`${this.url}/getAll`);
  }

  add(school: {name: string | null}): Observable<ResponseBody> {
    return this.httpClient.post<ResponseBody>(`${this.url}/add`, school);
  }

  getSchoolById(id: number): Observable<School[]> {
    return this.httpClient.get<School[]>(`${this.url}/${id}`);
  }

  updateSchool(id: number, name: string): Observable<School[]> {
    return this.httpClient.put<School[]>(`${this.url}/${id}`, name);
  }
}

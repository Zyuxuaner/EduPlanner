import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {School} from "../entity/school";

@Injectable({
  providedIn: 'root'
})
export class SchoolService {
  private url = 'http://localhost:8080/School';

  constructor(private httpClient: HttpClient) { }
  getAll(): Observable<School[]> {
    return this.httpClient.get<School[]>(`${this.url}/getAll`);
  }

  add(school: {name: string | null}): Observable<any> {
    return this.httpClient.post(`${this.url}/add`, school);
  }
}

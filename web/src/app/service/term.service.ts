import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Term} from "../entity/term";
import {School} from "../entity/school";

@Injectable({
  providedIn: 'root'
})
export class TermService {
  private baseUrl = '/term';

  constructor(private httpClient: HttpClient) {}

  add(term: {name: string, startTime: number, endTime: number, school: School}): Observable<Term> {
    return this.httpClient.post<Term>(`${this.baseUrl}/add`, term);
  }

  getAll(): Observable<Term[]> {
    return this.httpClient.get<Term[]>(`${this.baseUrl}/getAll`);
  }
}

import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Term} from "../entity/term";
import {School} from "../entity/school";
import {ResponseBody} from "../entity/response-body";

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

  // 根据学校获取当前激活学期的总周数
  getTermAndWeeks(schoolId: number): Observable<ResponseBody> {
    return this.httpClient.get<ResponseBody>(`${this.baseUrl}/getTermAndWeeks/${schoolId}`);
  }
}

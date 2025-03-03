import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {Term} from "../entity/term";
import {School} from "../entity/school";
import {ResponseBody} from "../entity/response-body";
import {Student} from "../entity/student";

@Injectable({
  providedIn: 'root'
})
export class TermService {
  private baseUrl = 'http://localhost:8080/Term';

  constructor(private httpClient: HttpClient) {}

  add(term: {name: string, startTime: number, endTime: number, school: School}): Observable<ResponseBody> {
    return this.httpClient.post<ResponseBody>(`${this.baseUrl}/add`, term);
  }

  active(termId: number): Observable<ResponseBody> {
    return this.httpClient.get<ResponseBody>(`${this.baseUrl}/active/${termId}`);
  }

  // 检查该用户所在学校是否有激活的学期
  checkTerm(): Observable<ResponseBody> {
    return this.httpClient.get<ResponseBody>(`${this.baseUrl}/checkTerm`);
  }

  deleteTerm(id: number): Observable<ResponseBody> {
    return this.httpClient.delete<ResponseBody>(`${this.baseUrl}/delete/${id}`);
  }

  getAll(): Observable<Term[]> {
    return this.httpClient.get<Term[]>(`${this.baseUrl}/getAll`);
  }

  search(schoolId: number | null, searchName: string): Observable<Term[]> {
    let params = new HttpParams();

    if (schoolId) {
      params = params.set('schoolId', schoolId);
    }

    if (searchName) {
      params = params.set('searchName', searchName);
    }

    return this.httpClient.get<Term[]>(`${this.baseUrl}/search`, { params });
  }

  getTermById(id: number): Observable<Term> {
    return this.httpClient.get<Term>(`${this.baseUrl}/getTermById/${id}`);
  }

  getAllSchoolIdsAndStartTime(): Observable<ResponseBody> {
    return this.httpClient.get<ResponseBody>(`${this.baseUrl}/getAllSchoolIdsAndStartTime`);
  }

  // 根据学校获取当前激活学期的总周数
  getTermAndWeeks(): Observable<ResponseBody> {
    return this.httpClient.get<ResponseBody>(`${this.baseUrl}/getTermAndWeeks`);
  }

  // 根据选中的学校id获取学期和周数
  getTermAndWeeksAndStudentsBySchoolId(schoolId: number): Observable<ResponseBody> {
    return this.httpClient.get<ResponseBody>(`${this.baseUrl}/getTermAndWeeksAndStudentsBySchoolId/${schoolId}`);
  }

  updateTerm(term: any): Observable<ResponseBody> {
    return this.httpClient.put<ResponseBody>(`${this.baseUrl}/updateTerm/${term.id}`, term);
  }
}

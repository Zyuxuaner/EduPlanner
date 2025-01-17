import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {ResponseBody} from "../entity/response-body";

@Injectable({
  providedIn: 'root'
})
export class CourseService {
  baseUrl = '/course';
  constructor(private httpClient: HttpClient) { }

  // 根据学校和周数，获取该学校所有学生的时间表
  getAllStudentsCourse(params: HttpParams): Observable<ResponseBody> {
    return this.httpClient.get<ResponseBody>(`${this.baseUrl}/getAllStudentsCourse`, {params});
  }
}

import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {ResponseBody} from "../entity/response-body";
import {Course} from "../entity/course";

@Injectable({
  providedIn: 'root'
})
export class CourseService {
  baseUrl = '/course';
  constructor(private httpClient: HttpClient) { }

  add(course: {
    name: string;
    type: number;
    status: number;
    start_week: number;
    end_week: number;
    week: number;
    begin: number;
    end: number
  }): Observable<ResponseBody> {
    return this.httpClient.post<ResponseBody>(`${this.baseUrl}/add`, course);
  }

  getAll(): Observable<Course[]> {
    return this.httpClient.get<Course[]>(`${this.baseUrl}/getAll`);
  }

  // 根据学校和周数，获取该学校所有学生的时间表
  getAllStudentsCourse(params: HttpParams): Observable<ResponseBody> {
    return this.httpClient.get<ResponseBody>(`${this.baseUrl}/getAllStudentsCourse`, {params});
  }
}

import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {ResponseBody} from "../entity/response-body";
import {Course} from "../entity/course";
import {schoolIdAndWeeks} from "../entity/schoolIdAndWeeks";
import {SaveRequest} from "../dto/courseDto/saveRequest";
import {GetAllResponse} from "../dto/courseDto/getAllResponse";

@Injectable({
  providedIn: 'root'
})
export class CourseService {
  baseUrl = 'http://localhost:8080/Course';
  constructor(private httpClient: HttpClient) { }

  add(course: SaveRequest): Observable<ResponseBody> {
    return this.httpClient.post<ResponseBody>(`${this.baseUrl}/add`, course);
  }

  delete(courseInfoId: number): Observable<ResponseBody> {
    return this.httpClient.delete<ResponseBody>(`${this.baseUrl}/delete/${courseInfoId}`);
  }

  getAll(): Observable<GetAllResponse[]> {
    return this.httpClient.get<GetAllResponse[]>(`${this.baseUrl}/getAll`);
  }

  // 根据学校和周数，获取该学校所有学生的时间表
  getCourseMessage(params: HttpParams): Observable<ResponseBody> {
    return this.httpClient.get<ResponseBody>(`${this.baseUrl}/getCourseMessage`, {params});
  }

  // 复用课程安排
  reuse(courseInfoId: number): Observable<ResponseBody> {
    return this.httpClient.post<ResponseBody>(`${this.baseUrl}/reuse/${courseInfoId}`, {});
  }

  // 取消复用
  cancelReuse(courseInfoId: number): Observable<ResponseBody> {
    return this.httpClient.post<ResponseBody>(`${this.baseUrl}/cancelReuse/${courseInfoId}`, {});
  }
}

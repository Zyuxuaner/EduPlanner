import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {ResponseBody} from "../entity/response-body";
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

  getCourseInfoById(id: number): Observable<ResponseBody> {
    return this.httpClient.get<ResponseBody>(`${this.baseUrl}/getCourseInfoById/${id}`);
  }

  // 根据学校和周数，获取该学校所有学生的时间表
  getCourseMessage(params: HttpParams): Observable<ResponseBody> {
    return this.httpClient.get<ResponseBody>(`${this.baseUrl}/getCourseMessage`, {params});
  }

  update(courseInfo: SaveRequest): Observable<ResponseBody> {
    return this.httpClient.patch<ResponseBody>(`${this.baseUrl}/update`, courseInfo);
  }

  // 复用课程安排
  reuse(courseInfoId: number): Observable<ResponseBody> {
    return this.httpClient.post<ResponseBody>(`${this.baseUrl}/reuse/${courseInfoId}`, {});
  }

  // 取消复用
  cancelReuse(courseInfoId: number): Observable<ResponseBody> {
    return this.httpClient.post<ResponseBody>(`${this.baseUrl}/cancelReuse/${courseInfoId}`, {});
  }

  getAllCourseInfo(schoolIdAndWeeksData: schoolIdAndWeeks[]): Observable<ResponseBody> {
    let params = new HttpParams();

    // 将每个 schoolId 和 weeks 添加到查询参数中
    schoolIdAndWeeksData.forEach(item => {
      params = params.append('schoolId', item.schoolId.toString());
      params = params.append('weeks', item.weeks.toString());
    });

    return this.httpClient.get<ResponseBody>(`${this.baseUrl}/getAllCourseInfo`, {params});
  }

  search(searchCourse: string | null, creatorStudent: number | null): Observable<GetAllResponse[]> {
    let params = new HttpParams();

    if (searchCourse) {
      params = params.set('searchCourse', searchCourse);
    }

    if (creatorStudent) {
      params = params.set('creatorStudent', creatorStudent);
    }

    return this.httpClient.get<GetAllResponse[]>(`${this.baseUrl}/search`, { params });
  }
}

import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {ResponseBody} from "../entity/response-body";
import {Course} from "../entity/course";
import {schoolIdAndWeeks} from "../entity/schoolIdAndWeeks";
import {CourseDto} from "../dto/courseDto";

@Injectable({
  providedIn: 'root'
})
export class CourseService {
  baseUrl = 'http://localhost:8080/Course';
  constructor(private httpClient: HttpClient) { }

  add(course: CourseDto): Observable<ResponseBody> {
    return this.httpClient.post<ResponseBody>(`${this.baseUrl}/add`, course);
  }

  getAll(): Observable<Course[]> {
    return this.httpClient.get<Course[]>(`${this.baseUrl}/getAll`);
  }

  // 根据学校和周数，获取该学校所有学生的时间表
  getAllStudentsCourse(params: HttpParams): Observable<ResponseBody> {
    return this.httpClient.get<ResponseBody>(`${this.baseUrl}/getAllStudentsCourse`, {params});
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

  getCourseInfoByStudent(week: number): Observable<ResponseBody> {
    return this.httpClient.get<ResponseBody>(`${this.baseUrl}/getCourseInfoByStudent`, {params: {week: week.toString()}});
  }
}

import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Student} from "../entity/student";
import {School, SchoolImpl} from "../entity/school";
import {ResponseBody} from "../entity/response-body";

@Injectable({
  providedIn: 'root'
})
export class StudentService {
  private baseUrl = 'http://localhost:8080/Student';
  constructor(private httpClient: HttpClient) { }

  add(student: {school: SchoolImpl, name: string, username: string, sno: string}): Observable<ResponseBody> {
    return this.httpClient.post<ResponseBody>(`${this.baseUrl}/add`, student);
  }

  changeStatus(id: number, status: number): Observable<Student> {
    return this.httpClient.put<Student>(`${this.baseUrl}/changeStatus/${id}`, { status });
  }

  delete(id: number): Observable<ResponseBody> {
    return this.httpClient.delete<ResponseBody>(`${this.baseUrl}/delete/${id}`);
  }

  getAll(): Observable<Student[]> {
    return this.httpClient.get<Student[]>(`${this.baseUrl}/getAll`);
  }

  getStudentById(id: number): Observable<Student> {
    return this.httpClient.get<Student>(`${this.baseUrl}/getStudentById/${id}`);
  }

  resetPassword(id: number, newPassword: string): Observable<ResponseBody> {
    return this.httpClient.patch<ResponseBody>(`${this.baseUrl}/resetPassword/${id}`, newPassword);
  }

  search(schoolId: number | null, clazzId: number | null, searchName: string, searchStudentSno: string): Observable<Student[]> {
    let url = `${this.baseUrl}/search?`;
    if (schoolId) {
      url += `schoolId=${schoolId}&`;
    }
    if (clazzId) {
      url += `clazzId=${clazzId}&`;
    }
    if (searchName) {
      url += `searchName=${searchName}&`;
    }
    if (searchStudentSno) {
      url += `searchStudentSno=${searchStudentSno}&`;
    }
    // 移除最后一个多余的 &
    url = url.replace(/&$/, '');
    return this.httpClient.get<Student[]>(url);
  }
}

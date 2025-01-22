import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Student} from "../entity/student";
import {Clazz} from "../entity/clazz";
import {School} from "../entity/school";

@Injectable({
  providedIn: 'root'
})
export class StudentService {
  private baseUrl = 'http://localhost:8080/Student';
  constructor(private httpClient: HttpClient) { }

  add(student: {school: School, clazz: Clazz, name: string, username: string, sno: string}): Observable<Student> {
    return this.httpClient.post<Student>(`${this.baseUrl}/add`, student);
  }

  getAll(): Observable<Student[]> {
    return this.httpClient.get<Student[]>(`${this.baseUrl}/getAll`);
  }
}

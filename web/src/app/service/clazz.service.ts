import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Clazz} from "../entity/clazz";
import {School} from "../entity/school";
import {ResponseBody} from "../entity/response-body";

@Injectable({
  providedIn: 'root'
})
export class ClazzService {
  private baseUrl = 'http://localhost:8080/Clazz';

  constructor(private httpClient: HttpClient) { }

  add(clazz: {name: string, school: School}): Observable<ResponseBody> {
    return this.httpClient.post<ResponseBody>(`${this.baseUrl}/add`, clazz);
  }

  getAll(): Observable<Clazz[]> {
    return this.httpClient.get<Clazz[]>(`${this.baseUrl}/getAll`);
  }

  getAllClazzBySchoolId(schoolId: number): Observable<Clazz[]> {
    console.log("service:", schoolId);
    return this.httpClient.get<Clazz[]>(`${this.baseUrl}/getClazzBySchoolId/${schoolId}`);
  }
}

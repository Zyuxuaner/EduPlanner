import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
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

  deleteClazz(id: number): Observable<ResponseBody> {
    return this.httpClient.delete<ResponseBody>(`${this.baseUrl}/delete/${id}`);
  }

  getAll(): Observable<Clazz[]> {
    return this.httpClient.get<Clazz[]>(`${this.baseUrl}/getAll`);
  }

  getAllClazzBySchoolId(schoolId: number): Observable<Clazz[]> {
    return this.httpClient.get<Clazz[]>(`${this.baseUrl}/getClazzBySchoolId/${schoolId}`);
  }

  getClazzByClazzId(clazzId: number): Observable<Clazz> {
    return this.httpClient.get<Clazz>(`${this.baseUrl}/${clazzId}`);
  }

  searchClazzes(schoolId: number | null, clazzName: string | null): Observable<Clazz[]> {
    let params = new HttpParams();
    if (schoolId) {
      params = params.set('schoolId', schoolId.toString());
    }
    if (clazzName) {
      params = params.set('clazzName', clazzName);
    }
    return this.httpClient.get<Clazz[]>(`${this.baseUrl}/search`, { params });
  }

  updateClazz(clazzId: number, schoolId: number, clazzName: string): Observable<ResponseBody> {
    const updatedClazz = { schoolId, name: clazzName };
    return this.httpClient.put<ResponseBody>(`${this.baseUrl}/update/${clazzId}`, updatedClazz);
  }
}

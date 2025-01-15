import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Clazz} from "../entity/clazz";

@Injectable({
  providedIn: 'root'
})
export class ClazzService {
  private baseUrl = '/clazz';

  constructor(private httpClient: HttpClient) { }

  add(clazz: {name: string, schoolId: number}): Observable<any> {
    return this.httpClient.post(`${this.baseUrl}/add`, clazz);
  }

  getAll(): Observable<Clazz[]> {
    return this.httpClient.get<Clazz[]>(`${this.baseUrl}/getAll`);
  }
}

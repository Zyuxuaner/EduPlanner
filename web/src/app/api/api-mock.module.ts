import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {HTTP_INTERCEPTORS} from "@angular/common/http";
import {MockApiInterceptor} from "@yunzhi/ng-mock-api";
import {AdminMockApi} from "./admin-mock-api";
import {TermMockApi} from "./term-mock-api";
import {SchoolMockApi} from "./school-mock-api";
import {StudentMockApi} from "./student-mock-api";
import {CourseMockApi} from "./course-mock-api";
import {LoginMockApi} from "./login-mock-api";

const api = [
  AdminMockApi,
  TermMockApi,
  SchoolMockApi,
  StudentMockApi,
  CourseMockApi,
  LoginMockApi
]

@NgModule({
  declarations: [],
  imports: [
    CommonModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      multi: true,
      useClass: MockApiInterceptor.forRoot(api)
    }
  ]
})
export class ApiMockModule { }

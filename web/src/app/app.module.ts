import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { LayoutComponent } from './layout/layout.component';
import {HeaderModule} from "./layout/header/header.module";
import {NavModule} from "./layout/nav/nav.module";
import {AdminModule} from "./admin/admin.module";
import {ApiMockModule} from "./api/api-mock.module";
import {HttpClientModule} from "@angular/common/http";
import {ClazzModule} from "./clazz/clazz.module";
import { NZ_I18N } from 'ng-zorro-antd/i18n';
import { zh_CN } from 'ng-zorro-antd/i18n';
import { registerLocaleData } from '@angular/common';
import zh from '@angular/common/locales/zh';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {SchoolSelectModule} from "./core/school-select/school-select.module";
import {ClazzSelectModule} from "./core/clazz-select/clazz-select.module";
import {CourseTableModule} from "./course-table/course-table.module";
import {CourseModule} from "./course/course.module";
import { LoginComponent } from './login/login.component';
import {SchoolModule} from "./school/school.module";
import {SchoolAddModule} from "./school-add/school-add.module";

registerLocaleData(zh);

@NgModule({
  declarations: [
    AppComponent,
    DashboardComponent,
    LayoutComponent,
    LoginComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HeaderModule,
    NavModule,
    AdminModule,
    ApiMockModule,
    HttpClientModule,
    ClazzModule,
    FormsModule,
    BrowserAnimationsModule,
    SchoolSelectModule,
    ClazzSelectModule,
    CourseTableModule,
    CourseModule,
    ReactiveFormsModule,
    ClazzSelectModule,
    SchoolModule,
    SchoolAddModule
  ],
  providers: [
    { provide: NZ_I18N, useValue: zh_CN }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }

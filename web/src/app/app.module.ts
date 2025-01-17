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
import { FormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {SchoolSelectModule} from "./core/school-select/school-select.module";
import {ClazzSelectModule} from "./core/clazz-select/clazz-select.module";
import { CourseTableComponent } from './course-table/course-table.component';
import {CourseTableModule} from "./course-table/course-table.module";

registerLocaleData(zh);

@NgModule({
  declarations: [
    AppComponent,
    DashboardComponent,
    LayoutComponent
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
    CourseTableModule
  ],
  providers: [
    { provide: NZ_I18N, useValue: zh_CN }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }

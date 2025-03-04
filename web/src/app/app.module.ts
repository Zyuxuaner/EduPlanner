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
import { NZ_I18N } from 'ng-zorro-antd/i18n';
import { zh_CN } from 'ng-zorro-antd/i18n';
import { registerLocaleData } from '@angular/common';
import zh from '@angular/common/locales/zh';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {SchoolSelectModule} from "./core/school-select/school-select.module";
import {CourseTableModule} from "./course-table/course-table.module";
import { LoginComponent } from './login/login.component';
import {AuthTokenInterceptorModule} from "./core/interceptor/auth-token-interceptor.module";
import {CourseModule} from "./course/course.module";
import {MeModule} from "./me/me.module";
import {MyScheduleModule} from "./my-schedule/my-schedule.module";
import {WeekSelectorModule} from "./core/week-selector/week-selector.module";
import { CeshiComponent } from './ceshi/ceshi.component';

registerLocaleData(zh);

@NgModule({
    declarations: [
        AppComponent,
        DashboardComponent,
        LayoutComponent,
        LoginComponent,
        CeshiComponent,
    ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        HeaderModule,
        NavModule,
        AdminModule,
        HttpClientModule,
        AuthTokenInterceptorModule,
        // ApiMockModule,
        FormsModule,
        BrowserAnimationsModule,
        SchoolSelectModule,
        CourseTableModule,
        ReactiveFormsModule,
        CourseModule,
        MeModule,
        MyScheduleModule,
        WeekSelectorModule
    ],
    providers: [
        {provide: NZ_I18N, useValue: zh_CN}
    ],
    bootstrap: [AppComponent]
})
export class AppModule { }

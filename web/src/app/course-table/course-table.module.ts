import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CourseTableRoutingModule } from './course-table-routing.module';
import {CourseTableComponent} from "./course-table.component";
import { AdminScheduleComponent } from './admin-schedule/admin-schedule.component';
import {SchoolSelectModule} from "../core/school-select/school-select.module";
import {NzSelectModule} from "ng-zorro-antd/select";
import {FormsModule} from "@angular/forms";
import { StudentScheduleComponent } from './student-schedule/student-schedule.component';


@NgModule({
  declarations: [CourseTableComponent, AdminScheduleComponent, StudentScheduleComponent],
  imports: [
    CommonModule,
    CourseTableRoutingModule,
    SchoolSelectModule,
    NzSelectModule,
    FormsModule
  ]
})
export class CourseTableModule { }

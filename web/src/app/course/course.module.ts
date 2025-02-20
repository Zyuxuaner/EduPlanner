import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CourseRoutingModule } from './course-routing.module';
import {CourseComponent} from "./course.component";
import {NzSelectModule} from "ng-zorro-antd/select";
import {ReactiveFormsModule} from "@angular/forms";
import {NzRadioModule} from "ng-zorro-antd/radio";
import {SchoolSelectModule} from "../core/school-select/school-select.module";
import {DayFormatPipe, WeekDayFormatPipe, WeekRangeFormatPipe, WeekTypeFormatPipe} from "../pipe/course-pipes";
import { EditComponent } from './edit/edit.component';
import {WeekSelectorModule} from "../core/week-selector/week-selector.module";


@NgModule({
  declarations: [CourseComponent, EditComponent],
  imports: [
    CommonModule,
    CourseRoutingModule,
    NzSelectModule,
    ReactiveFormsModule,
    NzRadioModule,
    SchoolSelectModule,
    WeekRangeFormatPipe,
    WeekTypeFormatPipe,
    DayFormatPipe,
    WeekDayFormatPipe,
    WeekSelectorModule
  ]
})
export class CourseModule { }

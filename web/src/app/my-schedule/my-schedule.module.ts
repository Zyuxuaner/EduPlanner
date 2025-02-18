import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MyScheduleRoutingModule } from './my-schedule-routing.module';
import {MyScheduleComponent} from "./my-schedule.component";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {NzSelectModule} from "ng-zorro-antd/select";
import {WeekSelectorModule} from "../core/week-selector/week-selector.module";
import * as fromPipes from '../pipe/course-pipes';

@NgModule({
  declarations: [
    MyScheduleComponent,

  ],
    imports: [
        CommonModule,
        MyScheduleRoutingModule,
        FormsModule,
        NzSelectModule,
        ReactiveFormsModule,
        WeekSelectorModule,
        fromPipes.DayFormatPipe,
        fromPipes.WeekDayFormatPipe
    ]
})
export class MyScheduleModule { }

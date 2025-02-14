import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MyScheduleRoutingModule } from './my-schedule-routing.module';
import {MyScheduleComponent} from "./my-schedule.component";
import {FormsModule} from "@angular/forms";

@NgModule({
  declarations: [MyScheduleComponent],
  imports: [
    CommonModule,
    MyScheduleRoutingModule,
    FormsModule,
  ]
})
export class MyScheduleModule { }

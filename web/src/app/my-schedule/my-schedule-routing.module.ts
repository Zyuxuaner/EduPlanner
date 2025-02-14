import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {MyScheduleComponent} from "./my-schedule.component";

const routes: Routes = [
  {
    path: '',
    component: MyScheduleComponent,
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MyScheduleRoutingModule { }

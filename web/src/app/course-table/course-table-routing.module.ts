import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {CourseTableComponent} from "./course-table.component";

const routes: Routes = [
  {
    path: '',
    component: CourseTableComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CourseTableRoutingModule { }

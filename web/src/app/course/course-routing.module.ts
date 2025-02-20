import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {CourseComponent} from "./course.component";
import {EditComponent} from "./edit/edit.component";

const routes: Routes = [
  {
    path: '',
    component: CourseComponent
  },
  {
    path: 'edit/:id',
    component: EditComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CourseRoutingModule { }

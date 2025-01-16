import { NgModule } from '@angular/core';
import {RouterModule, Routes} from "@angular/router";
import {StudentComponent} from "./student.component";
import {AddComponent} from "./add/add.component";

const routes: Routes = [
  {
    path: '',
    component: StudentComponent
  },
  {
    path: 'add',
    component: AddComponent
  }
]

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class StudentRoutingModule { }

import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {SchoolComponent} from "./school.component";
import {AddComponent} from "./add/add.component";

const routes: Routes = [
  {
    path: '',
    component: SchoolComponent
  },
  {
    path: 'add',
    component: AddComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SchoolRoutingModule { }

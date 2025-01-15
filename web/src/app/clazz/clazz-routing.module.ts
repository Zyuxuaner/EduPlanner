import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {ClazzComponent} from "./clazz.component";
import {AddComponent} from "./add/add.component";

const routes: Routes = [
  {
    path: '',
    component: ClazzComponent,
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
export class ClazzRoutingModule { }

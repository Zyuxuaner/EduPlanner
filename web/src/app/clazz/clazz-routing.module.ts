import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {ClazzComponent} from "./clazz.component";

const routes: Routes = [
  {
    path: '',
    component: ClazzComponent,
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ClazzRoutingModule { }

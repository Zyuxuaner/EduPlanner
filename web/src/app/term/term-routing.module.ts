import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {TermComponent} from './term.component';
import {AddComponent} from './add/add.component';
import {EditComponent} from "./edit/edit.component";

const routes: Routes = [
  {
    path: '',
    component: TermComponent
  },
  {
    path: 'add',
    component: AddComponent
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
export class TermRoutingModule { }

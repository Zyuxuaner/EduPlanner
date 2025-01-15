import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {TermComponent} from './term.component';
import {AddComponent} from './add/add.component';

const routes: Routes = [
  {
    path: '',
    component: TermComponent
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
export class TermRoutingModule { }

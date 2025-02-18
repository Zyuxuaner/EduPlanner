import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {AddComponent} from "./add/add.component";
import {AdminComponent} from "./admin.component";
import {EditComponent} from "./edit/edit.component";

const routes: Routes = [
  {
    path: '',
    component: AdminComponent
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
export class AdminRoutingModule { }

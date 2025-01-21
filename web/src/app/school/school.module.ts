import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SchoolRoutingModule } from './school-routing.module';
import {SchoolComponent} from "./school.component";
import { AddComponent } from './add/add.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {SchoolSelectModule} from "../core/school-select/school-select.module";


@NgModule({
  declarations: [SchoolComponent, AddComponent],
  imports: [
    CommonModule,
    SchoolRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    SchoolSelectModule
  ]
})
export class SchoolModule { }

import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {SchoolSelectComponent} from "./school-select.component";
import {NzSelectModule} from "ng-zorro-antd/select";
import {ReactiveFormsModule} from "@angular/forms";



@NgModule({
  declarations: [SchoolSelectComponent],
  exports: [
    SchoolSelectComponent
  ],
  imports: [
    CommonModule,
    NzSelectModule,
    ReactiveFormsModule
  ]
})
export class SchoolSelectModule { }

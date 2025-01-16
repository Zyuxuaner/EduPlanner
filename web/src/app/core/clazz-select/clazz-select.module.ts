import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {ClazzSelectComponent} from "./clazz-select.component";
import {NzSelectModule} from "ng-zorro-antd/select";
import {ReactiveFormsModule} from "@angular/forms";



@NgModule({
  declarations: [ClazzSelectComponent],
  exports: [
    ClazzSelectComponent
  ],
  imports: [
    CommonModule,
    NzSelectModule,
    ReactiveFormsModule
  ]
})
export class ClazzSelectModule { }

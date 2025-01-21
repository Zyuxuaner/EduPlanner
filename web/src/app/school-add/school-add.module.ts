import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {SchoolAddComponent} from "./school-add.component";
import {ReactiveFormsModule} from "@angular/forms";
import {SchoolAddRoutingModule} from "./school-add-routing.module";

@NgModule({
  declarations: [
    SchoolAddComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    SchoolAddRoutingModule
  ]
})
export class SchoolAddModule { }

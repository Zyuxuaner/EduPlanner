import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {SchoolAddComponent} from "./school-add.component";
import {ReactiveFormsModule} from "@angular/forms";

@NgModule({
  declarations: [
    SchoolAddComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule
  ]
})
export class SchoolAddModule { }

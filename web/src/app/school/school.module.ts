import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {SchoolComponent} from "./school.component";
import {RouterModule} from "@angular/router";
import {FormsModule} from "@angular/forms";

@NgModule({
  declarations: [
    SchoolComponent
  ],
  imports: [
    CommonModule,
    RouterModule,
    FormsModule
  ]
})
export class SchoolModule { }

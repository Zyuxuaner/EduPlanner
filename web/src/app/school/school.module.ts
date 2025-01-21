import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {SchoolComponent} from "./school.component";
import {RouterModule} from "@angular/router";
import {FormsModule} from "@angular/forms";
import {SchoolRoutingModule} from "./school-routing.module";

@NgModule({
  declarations: [
    SchoolComponent
  ],
  imports: [
    CommonModule,
    RouterModule,
    FormsModule,
    SchoolRoutingModule
  ]
})
export class SchoolModule { }

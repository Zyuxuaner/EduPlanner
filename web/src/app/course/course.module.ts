import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CourseRoutingModule } from './course-routing.module';
import {CourseComponent} from "./course.component";
import {NzSelectModule} from "ng-zorro-antd/select";
import {ReactiveFormsModule} from "@angular/forms";
import { AddComponent } from './add/add.component';
import {NzRadioModule} from "ng-zorro-antd/radio";
import {SchoolSelectModule} from "../core/school-select/school-select.module";


@NgModule({
  declarations: [CourseComponent, AddComponent],
  imports: [
    CommonModule,
    CourseRoutingModule,
    NzSelectModule,
    ReactiveFormsModule,
    NzRadioModule,
    SchoolSelectModule
  ]
})
export class CourseModule { }

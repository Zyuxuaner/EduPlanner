import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { StudentComponent } from './student.component';
import {StudentRoutingModule} from "./student-routing.module";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {ClazzSelectModule} from "../core/clazz-select/clazz-select.module";
import {SchoolSelectModule} from "../core/school-select/school-select.module";
import { AddComponent } from './add/add.component';
import { EditComponent } from './edit/edit.component';



@NgModule({
  declarations: [
    StudentComponent,
    AddComponent,
    EditComponent
  ],
    imports: [
        CommonModule,
        StudentRoutingModule,
        FormsModule,
        ReactiveFormsModule,
        ClazzSelectModule,
        SchoolSelectModule
    ]
})
export class StudentModule { }

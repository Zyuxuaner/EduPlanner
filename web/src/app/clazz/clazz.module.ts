import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ClazzRoutingModule } from './clazz-routing.module';
import {ClazzComponent} from "./clazz.component";
import {ReactiveFormsModule} from "@angular/forms";
import {SchoolSelectModule} from "../core/school-select/school-select.module";
import { AddComponent } from './add/add.component';


@NgModule({
  declarations: [
    ClazzComponent,
    AddComponent
  ],
    imports: [
        CommonModule,
        ClazzRoutingModule,
        ReactiveFormsModule,
        SchoolSelectModule
    ]
})
export class ClazzModule { }

import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { TermRoutingModule } from './term-routing.module';
import { TermComponent } from './term.component';
import { AddComponent } from './add/add.component';
import {ReactiveFormsModule} from '@angular/forms';
import {NzDatePickerModule} from "ng-zorro-antd/date-picker";
import {SchoolSelectModule} from "../core/school-select/school-select.module";
import { EditComponent } from './edit/edit.component';


@NgModule({
  declarations: [
    TermComponent,
    AddComponent,
    EditComponent
  ],
    imports: [
        CommonModule,
        TermRoutingModule,
        NzDatePickerModule,
        ReactiveFormsModule,
        SchoolSelectModule
    ]
})
export class TermModule { }

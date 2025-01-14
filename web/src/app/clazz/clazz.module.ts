import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ClazzRoutingModule } from './clazz-routing.module';
import {ClazzComponent} from "./clazz.component";
import {ReactiveFormsModule} from "@angular/forms";


@NgModule({
  declarations: [ClazzComponent],
  imports: [
    CommonModule,
    ClazzRoutingModule,
    ReactiveFormsModule
  ]
})
export class ClazzModule { }

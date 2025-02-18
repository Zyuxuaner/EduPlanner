import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {WeekSelectorComponent} from "./week-selector.component";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {NzButtonModule} from "ng-zorro-antd/button";
import {NzCheckboxModule} from "ng-zorro-antd/checkbox";
import {NzRadioModule} from "ng-zorro-antd/radio";
import {NzFormModule} from "ng-zorro-antd/form";



@NgModule({
  declarations: [WeekSelectorComponent],
  exports: [
    WeekSelectorComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    NzButtonModule,
    NzCheckboxModule,
    NzRadioModule,
    NzFormModule,
    FormsModule
  ]
})
export class WeekSelectorModule { }

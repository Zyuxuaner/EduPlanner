import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MeComponent } from './me.component';
import {FormsModule} from "@angular/forms";



@NgModule({
  declarations: [
    MeComponent
  ],
  imports: [
    CommonModule,
    FormsModule
  ]
})
export class MeModule { }

import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MeComponent } from './me.component';
import {FormsModule} from "@angular/forms";
import {MeRoutingModule} from "./me-routing.module";



@NgModule({
  declarations: [
    MeComponent
  ],
  imports: [
    CommonModule,
    MeRoutingModule,
    FormsModule
  ]
})
export class MeModule { }

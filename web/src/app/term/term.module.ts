import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { TermRoutingModule } from './term-routing.module';
import { TermComponent } from './term.component';


@NgModule({
  declarations: [
    TermComponent
  ],
  imports: [
    CommonModule,
    TermRoutingModule
  ]
})
export class TermModule { }

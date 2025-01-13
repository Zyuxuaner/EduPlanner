import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { LayoutComponent } from './layout/layout.component';
import {HeaderModule} from "./layout/header/header.module";
import {NavModule} from "./layout/nav/nav.module";
import {AdminModule} from "./admin/admin.module";
import {ApiMockModule} from "./api/api-mock.module";
import {HttpClientModule} from "@angular/common/http";
import {ClazzModule} from "./clazz/clazz.module";

@NgModule({
  declarations: [
    AppComponent,
    DashboardComponent,
    LayoutComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HeaderModule,
    NavModule,
    AdminModule,
    ApiMockModule,
    HttpClientModule,
    ClazzModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }

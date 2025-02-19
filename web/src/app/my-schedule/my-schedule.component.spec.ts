import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MyScheduleComponent } from './my-schedule.component';
import {WeekSelectorModule} from "../core/week-selector/week-selector.module";
import {NzRadioModule} from "ng-zorro-antd/radio";
import {ReactiveFormsModule} from "@angular/forms";
import {DayFormatPipe, WeekDayFormatPipe} from "../pipe/course-pipes";
import {HttpClientTestingModule} from "@angular/common/http/testing";
import * as $ from 'jquery';

describe('MyScheduleComponent', () => {
  let component: MyScheduleComponent;
  let fixture: ComponentFixture<MyScheduleComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MyScheduleComponent ],
      imports: [
        HttpClientTestingModule,
        WeekSelectorModule,
        NzRadioModule,
        ReactiveFormsModule,
        DayFormatPipe,
        WeekDayFormatPipe
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MyScheduleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

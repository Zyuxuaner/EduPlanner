import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CourseComponent } from './course.component';
import {HttpClientTestingModule} from "@angular/common/http/testing";
import {ReactiveFormsModule} from "@angular/forms";
import {NzSelectModule} from "ng-zorro-antd/select";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {WeekRangeFormatPipe, WeekTypeFormatPipe} from "../pipe/course-pipes";

describe('CourseComponent', () => {
  let component: CourseComponent;
  let fixture: ComponentFixture<CourseComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CourseComponent ],
      imports: [
        HttpClientTestingModule,
        ReactiveFormsModule,
        NzSelectModule,
        BrowserAnimationsModule,
        WeekRangeFormatPipe,
        WeekTypeFormatPipe
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CourseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

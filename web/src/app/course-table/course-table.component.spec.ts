import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CourseTableComponent } from './course-table.component';
import {HttpClientTestingModule} from "@angular/common/http/testing";
import {SchoolSelectModule} from "../core/school-select/school-select.module";
import {NzSelectModule} from "ng-zorro-antd/select";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";

describe('CourseTableComponent', () => {
  let component: CourseTableComponent;
  let fixture: ComponentFixture<CourseTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CourseTableComponent ],
      imports: [
        HttpClientTestingModule,
        SchoolSelectModule,
        NzSelectModule,
        FormsModule,
        ReactiveFormsModule,
        BrowserAnimationsModule
      ],
    })
    .compileComponents();

    fixture = TestBed.createComponent(CourseTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

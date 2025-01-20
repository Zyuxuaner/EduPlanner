import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CourseComponent } from './course.component';
import {HttpClientTestingModule} from "@angular/common/http/testing";
import {ReactiveFormsModule} from "@angular/forms";
import {NzAffixModule} from "ng-zorro-antd/affix";
import {NzSelectModule} from "ng-zorro-antd/select";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";

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
        BrowserAnimationsModule
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

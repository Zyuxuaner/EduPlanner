import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddComponent } from './add.component';
import {HttpClientTestingModule} from "@angular/common/http/testing";
import {NzDatePickerModule} from "ng-zorro-antd/date-picker";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {SchoolModule} from "../../school/school.module";
import {NzSelectModule} from "ng-zorro-antd/select";
import {SchoolSelectModule} from "../../core/school-select/school-select.module";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";

describe('AddComponent', () => {
  let component: AddComponent;
  let fixture: ComponentFixture<AddComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule,
        ReactiveFormsModule,
        NzDatePickerModule,
        SchoolSelectModule,
        BrowserAnimationsModule
      ],
      declarations: [ AddComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ClazzComponent } from './clazz.component';
import {HttpClientTestingModule} from "@angular/common/http/testing";
import {SchoolSelectModule} from "../core/school-select/school-select.module";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {ReactiveFormsModule} from "@angular/forms";
import {RouterTestingModule} from "@angular/router/testing";

describe('ClazzComponent', () => {
  let component: ClazzComponent;
  let fixture: ComponentFixture<ClazzComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ClazzComponent ],
      imports: [
        HttpClientTestingModule,
        SchoolSelectModule,
        BrowserAnimationsModule,
        ReactiveFormsModule,
        RouterTestingModule
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ClazzComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

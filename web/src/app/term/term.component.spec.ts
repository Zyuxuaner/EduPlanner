import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TermComponent } from './term.component';
import {HttpClientTestingModule} from "@angular/common/http/testing";
import {RouterTestingModule} from "@angular/router/testing";
import {SchoolSelectModule} from "../core/school-select/school-select.module";
import {ReactiveFormsModule} from "@angular/forms";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";

describe('TermComponent', () => {
  let component: TermComponent;
  let fixture: ComponentFixture<TermComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule,
        RouterTestingModule,
        SchoolSelectModule,
        ReactiveFormsModule,
        BrowserAnimationsModule
      ],
      declarations: [ TermComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TermComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

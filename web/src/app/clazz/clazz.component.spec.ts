import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ClazzComponent } from './clazz.component';
import {RouterModule} from "@angular/router";
import {HttpClientTestingModule} from "@angular/common/http/testing";
import {SchoolSelectModule} from "../core/school-select/school-select.module";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";

describe('ClazzComponent', () => {
  let component: ClazzComponent;
  let fixture: ComponentFixture<ClazzComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ClazzComponent ],
      imports: [
        HttpClientTestingModule,
        SchoolSelectModule,
        BrowserAnimationsModule
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

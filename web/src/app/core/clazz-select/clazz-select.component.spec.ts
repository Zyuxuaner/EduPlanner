import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ClazzSelectComponent } from './clazz-select.component';
import {HttpClientTestingModule} from "@angular/common/http/testing";
import {NzSelectModule} from "ng-zorro-antd/select";
import {ReactiveFormsModule} from "@angular/forms";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";

describe('ClazzSelectComponent', () => {
  let component: ClazzSelectComponent;
  let fixture: ComponentFixture<ClazzSelectComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ClazzSelectComponent ],
      imports: [
        HttpClientTestingModule,
        NzSelectModule,
        ReactiveFormsModule,
        BrowserAnimationsModule
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ClazzSelectComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

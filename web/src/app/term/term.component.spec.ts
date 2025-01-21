import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TermComponent } from './term.component';
import {HttpClientTestingModule} from "@angular/common/http/testing";
import {SchoolModule} from "../school/school.module";
import {SchoolComponent} from "../school/school.component";

describe('TermComponent', () => {
  let component: TermComponent;
  let fixture: ComponentFixture<TermComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule,
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

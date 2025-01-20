import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SchoolComponent } from './school.component';
import {ReactiveFormsModule} from "@angular/forms";

describe('SchoolComponent', () => {
  let component: SchoolComponent;
  let fixture: ComponentFixture<SchoolComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SchoolComponent ],
      imports: [ ReactiveFormsModule ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SchoolComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WeekSelectorComponent } from './week-selector.component';
import {FormsModule} from "@angular/forms";

describe('WeekSelectorComponent', () => {
  let component: WeekSelectorComponent;
  let fixture: ComponentFixture<WeekSelectorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ WeekSelectorComponent ],
      imports: [ FormsModule ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(WeekSelectorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

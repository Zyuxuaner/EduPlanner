import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CeshiComponent } from './ceshi.component';

describe('CeshiComponent', () => {
  let component: CeshiComponent;
  let fixture: ComponentFixture<CeshiComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CeshiComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CeshiComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

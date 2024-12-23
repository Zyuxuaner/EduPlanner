import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LayoutComponent } from './layout.component';
import {HeaderModule} from "./header/header.module";
import {NavModule} from "./nav/nav.module";
import {HeaderComponent} from "./header/header.component";
import {NavComponent} from "./nav/nav.component";
import {RouterTestingModule} from "@angular/router/testing";

describe('LayoutComponent', () => {
  let component: LayoutComponent;
  let fixture: ComponentFixture<LayoutComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [
        LayoutComponent,
        HeaderComponent,
        NavComponent
      ],
      imports: [
        HeaderModule,
        NavModule,
        RouterTestingModule
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LayoutComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

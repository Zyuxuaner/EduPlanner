import { TestBed } from '@angular/core/testing';

import { SchoolService } from './school.service';
import {HttpClientTestingModule} from "@angular/common/http/testing";

describe('SchoolService', () => {
  let service: SchoolService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ HttpClientTestingModule ],
      providers: [ SchoolService ]
    })
      .compileComponents();
  });

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SchoolService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

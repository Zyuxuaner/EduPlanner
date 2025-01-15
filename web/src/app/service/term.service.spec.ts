import { TestBed } from '@angular/core/testing';

import { TermService } from './term.service';
import {HttpClientTestingModule} from "@angular/common/http/testing";

describe('TermService', () => {
  let service: TermService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [TermService]
    })
    .compileComponents();
  });

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TermService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

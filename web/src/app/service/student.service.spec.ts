import { TestBed } from '@angular/core/testing';

import { StudentService } from './student.service';
import {HttpClientTestingModule} from "@angular/common/http/testing";
import {TermService} from "./term.service";

describe('StudentService', () => {
  let service: StudentService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [StudentService]
    })
      .compileComponents();
  });

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(StudentService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

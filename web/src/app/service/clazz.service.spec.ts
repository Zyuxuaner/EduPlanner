import { TestBed } from '@angular/core/testing';

import { ClazzService } from './clazz.service';
import {HttpClientTestingModule} from "@angular/common/http/testing";
import {AdminService} from "./admin.service";

describe('ClazzService', () => {
  let service: ClazzService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ HttpClientTestingModule ],
      providers: [ ClazzService ]
    })
      .compileComponents();
  });

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ClazzService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

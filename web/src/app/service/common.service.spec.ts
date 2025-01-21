import { TestBed } from '@angular/core/testing';

import { CommonService } from './common.service';
import {HttpClientTestingModule} from "@angular/common/http/testing";
import {ClazzService} from "./clazz.service";

describe('CommonService', () => {
  let service: CommonService;
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      providers: [ CommonService ]
    })
      .compileComponents();
  });


  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CommonService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

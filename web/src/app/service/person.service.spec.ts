import { TestBed } from '@angular/core/testing';

import { PersonService } from './person.service';
import {HttpClientTestingModule} from "@angular/common/http/testing";
import {LoginService} from "./login.service";

describe('PersonService', () => {
  let service: PersonService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ HttpClientTestingModule ],
      providers: [ PersonService ]
    })
      .compileComponents();
  });

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PersonService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

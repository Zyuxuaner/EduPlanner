import { AuthTokenInterceptor } from './auth-token-interceptor';
import {TestBed} from "@angular/core/testing";
import {LoginService} from "../../service/login.service";
import {LoginStubService} from "../../service/login-stub.service";

describe('AuthTokenInterceptor', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      providers: [
        {provide: LoginService, useClass: LoginStubService}
      ]
    })
      .compileComponents();
  });
  it('should create an instance', () => {
    const loginService: LoginService = TestBed.get(LoginService);
    expect(new AuthTokenInterceptor(loginService)).toBeTruthy();
  });
});

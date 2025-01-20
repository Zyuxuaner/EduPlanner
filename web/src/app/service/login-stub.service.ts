import {Observable, of} from "rxjs";
import {ResponseBody, ResponseBodyImpl} from "../entity/response-body";

export class LoginStubService {
  constructor() {
  }

  login(user: {username: string, password: string}): Observable<ResponseBody> {
    return of(new ResponseBodyImpl(true, 'message', 'data'));
  }

  setIsLogin(isLogin: boolean) {
    return null;
  }

  logout(): Observable<ResponseBody> {
    return of(new ResponseBodyImpl(true, 'message', 'data'));
  }
 }

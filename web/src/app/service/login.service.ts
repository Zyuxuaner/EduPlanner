import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {BehaviorSubject, Observable, ReplaySubject, tap} from "rxjs";
import {ResponseBody} from "../entity/response-body";

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  // 数据源
  private isLogin: BehaviorSubject<boolean>;

  // 数据源对应的订阅服务
  public isLogin$: Observable<boolean>;

  private isLoginCacheKey = 'isLogin';
  constructor(private httpClient: HttpClient) {
    const isLogin: string | null = window.sessionStorage.getItem(this.isLoginCacheKey);
    this.isLogin = new BehaviorSubject(this.convertStringToBoolean(isLogin));
    this.isLogin$ = this.isLogin.asObservable();
  }

  login(user: {username: string, password: string}): Observable<ResponseBody> {
    const url = '/login';
    return this.httpClient.post<ResponseBody>(url, user)
      .pipe(tap( response => {
        if (response.data) {
          const user = {
            username: response.data.username,
            password: response.data.password
          };
          this.setIsLogin(true);
        }
      }));
  }

  /**
   * 设置登录状态
   * @param isLogin
   */
  setIsLogin(isLogin: boolean) {
    // sessionStorage只能存储string类型，所以将isLogin转换为字符串'0','1'进行存储
    window.sessionStorage.setItem(this.isLoginCacheKey, this.convertBooleanToString(isLogin));
    // 接收到新的登录状态时，向所有的订阅者们发送最新的登录状态的值
    this.isLogin.next(isLogin);
  }

  /**
   * 字符串转换为boolean
   * @param value 字符串
   * @return 1 true; 其它 false
   */
  convertStringToBoolean(value: string | null) {
    return value === '1';
  }

  /**
   * boolean转string
   * @param value boolean
   * @return '1' true; '0' false;
   */
  convertBooleanToString(value: boolean) {
    return value ? '1' : '0';
  }

  logout(): Observable<ResponseBody> {
    const url = '/logout';
    return this.httpClient.get<ResponseBody>(url)
      .pipe(tap( response => {
        if (response.status) {
          this.setIsLogin(false);
        }
      }));
  }
}

import { Injectable } from '@angular/core';
import {LoginService} from "./login.service";

/**
 * 缓存
 */
@Injectable({
  providedIn: 'root'
})
export class CacheService {
  // 认证令牌
  private static authToken: string | null = localStorage.getItem('authToken');

  constructor() { }

  static setAuthToken(token: string | null) {
    CacheService.authToken = token;
    if (token !== null) {
      localStorage.setItem('authToken', token);
    } else {
      localStorage.removeItem('authToken');
    }
  }

  static getAuthToken() {
    if (CacheService.authToken === null) {
      return '';
    }
    return CacheService.authToken;
  }
}

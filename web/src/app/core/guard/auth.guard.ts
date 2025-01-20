import { Injectable } from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from '@angular/router';
import {map, Observable} from 'rxjs';
import {LoginService} from "../../service/login.service";

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  constructor(private loginService: LoginService,
              private router: Router) {
  }

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    return this.loginService.isLogin$.pipe(map(login => {
      if (login) {
        console.log(login);
        return true;
      } else {
        console.log('用户未认证');
        // 获取当前尝试访问的url
        const redirectUrl = state.url;
        // 返回一个UrlTree进行重定向
        return this.router.createUrlTree(['/login'], { queryParams: { redirectUrl } });
      }
    }));
  }
}

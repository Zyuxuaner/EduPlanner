import {Injectable} from "@angular/core";
import {
  HttpErrorResponse,
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest,
  HttpResponse
} from "@angular/common/http";
import {map, Observable, tap} from "rxjs";
import {CacheService} from "../../service/cache.service";
import {LoginService} from "../../service/login.service";

@Injectable()
export class AuthTokenInterceptor implements HttpInterceptor{

  constructor(private loginService: LoginService) {
  }
  /**
   * @param req 上一个处理者传递过来的请求信息
   * @param next 下一个处理者（可能是一个拦截器，也可能直接转发给后台）
   */
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    console.log('拦截到请求信息。请求地址：' + req.url + '；请求方法：' + req.method);
    const reqClone = req.clone({
      setHeaders: {'auth-token': CacheService.getAuthToken()}
    });
    // 将信息传递个下一个处理者 使用pipe设置操作数据的操作符 map操作符的作用是将return的结果进行转发
    return next.handle(reqClone).pipe(map((httpEvent) => {
      // 检查httpEvent引用的对象是否是HttpResponse类的一个实例，如果是，表达式的结果为true
      if (httpEvent instanceof HttpResponse) {
        const httpResponse = httpEvent as HttpResponse<any>;
        console.log(httpResponse);
        const authToken = httpResponse.headers.get('auth-token');
        console.log(authToken);
        CacheService.setAuthToken(authToken);
      }

      return httpEvent;
    }), tap(() => {
    }, (event: HttpErrorResponse) => {
      if (event.status === 401) {
        this.loginService.setIsLogin(false);
      }
    }));
  }
}

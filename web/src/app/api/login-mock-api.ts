import {ApiInjector, MockApiInterface} from "@yunzhi/ng-mock-api";

export class LoginMockApi implements MockApiInterface {
  getInjectors(): ApiInjector[] {
    return [
      {
        method: 'POST',
        url: '/login',
        result: (urlMatcher: any, options: {body: {username: string, password: string}}) => {
          const user = options.body;
          return {
            status: true,
            message: '登录成功',
            data: {
              username: user.username,
              password: user.password,
              role: 1
            }
          }
        }
      },
      {
        method: 'GET',
        url: '/logout',
        result: (urlMatcher: any) => {
          return {
            status: true,
            message: '注销成功',
            data: null
          }
        }
      }
    ];
  }
}

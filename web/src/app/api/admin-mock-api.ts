import {ApiInjector, MockApiInterface} from "@yunzhi/ng-mock-api";
import {Admin} from "../entity/admin";

export class AdminMockApi implements MockApiInterface {
  getInjectors(): ApiInjector[] {
    return [
      {
        method: 'GET',
        url: '/admin/getAll',
        result: [
          { id: 1, name: '梦云智', ano: 123456, user: { id: 1, username: 'mengyunzhi', password: '12345', role: 1} },
          { id: 2, name: '李四', ano: 873624, user: { id: 2, username: 'lisi', password: '873624', role: 2} },
          { id: 3, name: '王五', ano: 983645, user: { id: 3, username: 'wangwu', password: '983645', role: 2} },
          { id: 4, name: '赵六', ano: 883762, user: { id: 4, username: 'zhaoliu', password: '883762', role: 2} },
        ]
      },
      {
        method: 'POST',
        url: '/admin/add',
        result: (urlMatcher: any, options: {body: {name: string, username: string, ano: number, role: number}}) => {
          let body = options.body;
          console.log(body);
          return  {
            id: Math.floor(Math.random() * 10000),
            username: body.username,
            name: body.name,
            ano: body.ano,
            role: body.role
          };
        }
      }
    ];
  }
}

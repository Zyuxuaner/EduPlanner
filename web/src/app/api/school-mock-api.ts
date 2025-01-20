import {ApiInjector, MockApiInterface} from "@yunzhi/ng-mock-api";

export class SchoolMockApi implements MockApiInterface {
    getInjectors(): ApiInjector[] {
      return [
        {
          method: 'GET',
          url: '/school/getAll',
          result: [
            {id: 1, name: '天津职业技术师范大学'},
            {id: 2, name: '河北工业大学'},
            {id: 3, name: '河北师范大学'},
            {id: 4, name: '河海大学'},
          ]
        },
        {
          method: 'POST',
          url: '/school/add',
          result: (urlMatcher: any, options: {body: {name: string, id: number}}) => {
            let body = options.body;
            console.log(body);
            return {
              id: Math.floor(Math.random() * 10000),
              name: body.name
            }
          }
        },
      ]
    }
}

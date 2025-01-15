import {ApiInjector, MockApiInterface} from "@yunzhi/ng-mock-api";
import {School} from "../entity/school";

export class ClazzMockApi implements MockApiInterface {
  getInjectors(): ApiInjector[] {
    return [
      {
        method: 'GET',
        url: '/clazz/getAll',
        result: [
          { id: 1, school: {id: 1, name: '天津职业技术师范大学'}, name: '大数据2301班' },
          { id: 2, school: {id: 1, name: '天津职业技术师范大学'}, name: '计科2301班' },
          { id: 3, school: {id: 2, name: '河北工业大学'}, name: '媒体2301班' }
        ]
      },
      {
        method: 'POST',
        url: '/clazz/add',
        result: (urlMatcher: any, options: {body: {name: string, schoolId: number}}) => {
          let body = options.body;
          console.log(body);
          return {
            id: Math.floor(Math.random() * 10000),
            name: body.name,
            schoolId: body.schoolId
          }
        }
      }
    ]
  }
}

import {ApiInjector, MockApiInterface} from "@yunzhi/ng-mock-api";

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
      }
    ]
  }
}

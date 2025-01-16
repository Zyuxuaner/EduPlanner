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
          { id: 3, school: {id: 2, name: '河北工业大学'}, name: '媒体2301班' },
          { id: 4, school: {id: 4, name: '河海大学'}, name: '教育2301班'}
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
      },
      {
        method: 'GET',
        url: '/clazz/getClazzBySchoolId/:schoolId',
        result: (urlMatcher: any) => {
          console.log('URL schoolId:', urlMatcher.schoolId);
          // 直接从 urlMatcher.url 中提取 schoolId
          const schoolId = parseInt(urlMatcher.schoolId, 10);

          // 根据 schoolId 返回相应的班级数据
          const clazzData: Record<number, { id: number; school: { id: number; name: string }; name: string }[]> = {
            1: [
              { id: 1, school: {id: 1, name: '天津职业技术师范大学'}, name: '大数据2301班' },
              { id: 2, school: {id: 1, name: '天津职业技术师范大学'}, name: '计科2301班' }
            ],
            2: [
              { id: 3, school: {id: 2, name: '河北工业大学'}, name: '媒体2301班' }
            ],
            4: [
              { id: 4, school: {id: 4, name: '河海大学'}, name: '教育2301班'}
            ]
          };
          // 返回对应 schoolId 的班级数据，若没有数据则返回空数组
          return clazzData[schoolId] || [];
        }
      }
    ]
  }
}

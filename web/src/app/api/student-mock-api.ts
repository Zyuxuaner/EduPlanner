import {ApiInjector, MockApiInterface} from "@yunzhi/ng-mock-api";

export class StudentMockApi implements MockApiInterface {
  getInjectors(): ApiInjector[] {
    return [
      {
        method: 'GET',
        url: '/student/getAll',
        result: [
          {
            id: 1, name: '张三', sno: '123333', status: 1,
            clazz: {id: 1, name: '班级1', school: {id: 1, name: '天津职业技术师范大学'}},
            user: {id: 1, username: 'zhangsan', password: '123333', role: 3}
          },
          {
            id: 2, name: '李四', sno: '123444', status: 1,
            clazz: {id: 2, name: '班级2', school: {id: 2, name: '河北工业大学'}},
            user: {id: 2, username: 'lisi', password: '123444', role: 3}
          },
          {
            id: 3, name: '王五', sno: '123455', status: 0,
            clazz: {id: 3, name: '班级3', school: {id: 1, name: '天津职业技术师范大学'}},
            user: {id: 1, username: 'wangwu', password: '123455', role: 3}
          },
        ]
      }
    ];
  }
}

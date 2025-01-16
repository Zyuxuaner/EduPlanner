import {ApiInjector, MockApiInterface} from "@yunzhi/ng-mock-api";
import {School} from "../entity/school";

export class TermMockApi implements MockApiInterface {
  getInjectors(): ApiInjector[] {
    return [
      {
        method: 'GET',
        url: '/term/getAll',
        result: [
          {id: 1, name: '2024年秋', startTime: 1725120000000, endTime: 1735488000000, status: 0, school: {id: 1, name: '天职师大'}},
          {id: 2, name: '2024年秋', startTime: 1725120000000, endTime: 1733760000000, status: 0, school: {id: 1, name: '河工大'}},
          {id: 3, name: '2024年春', startTime: 1709222400000, endTime: 1717171200000, status: 1, school: {id: 1, name: '天职师大'}}
        ]
      },
      {
        method: 'POST',
        url: '/term/add',
        result: (urlMatcher: any, options: {body: {name: string, startTime: number, endTime: number, school: School}}) => {
          const term = options.body;
          return {
            id: Math.floor(Math.random() * 10000),
            name: term.name,
            startTime: term.startTime,
            endTime: term.endTime,
            school: {
              id: term.school.id,
              name: '随机学校'
            },
          }
        }
      }
    ];
  }
}

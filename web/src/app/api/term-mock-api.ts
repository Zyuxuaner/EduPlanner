import {ApiInjector, MockApiInterface} from "@yunzhi/ng-mock-api";
import {School} from "../entity/school";
import {Term} from "../entity/term";

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
      },
      {
        method: 'GET',
        url: '/term/getTermAndWeeks/:schoolId',
        result: (urlMatcher: any) => {
          const schoolId = parseInt(urlMatcher.schoolId);
          const weeksData: Record<number, { status: boolean; message: string, data: { weeks: number[], term: {}}}> = {
            1: {status: true, message: '成功', data: {weeks: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16], term: {name:'天职师大2024年秋'}}},
            2: {status: true, message: '成功', data: {weeks: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18], term: {name: '河工大2024年秋'}}},
            3: {status: true, message: '成功', data: {weeks: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17 ,18, 19, 20], term: {name: '河师大2024年秋'}}},
            4: {status: true, message: '成功', data: {weeks: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20], term: {name: '海河大学2024年秋'}}},
          };
          return weeksData[schoolId] || {status: false, messeage: '计算失败，不存在激活学期'};
        }
      },
      {
        method: 'GET',
        url: '/term/checkTerm',
        result: { status: true, message: '成功', data: { term: {name: '河师大2024年秋', status: 1}, school: {name: '海河大学'}}}
         // { status: false, message: '你的学校还未开学', data: { term: {name: '河师大2024年秋', status: 1}, school: {name: '海河大学'}}}
      }
    ];
  }
}

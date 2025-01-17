import {ApiInjector, MockApiInterface} from "@yunzhi/ng-mock-api";
import {HttpParams} from "@angular/common/http";

export class CourseMockApi implements MockApiInterface {
    getInjectors(): ApiInjector[] {
        return [
          {
            method: 'GET',
            url: '/course/getAllStudentsCourse',
            result: (urlMather: any, option: { params: HttpParams}) => {
              const schoolId = option.params.get('schoolId');
              const week = option.params.get('week');

              console.log("mock schoolId:", schoolId); // 打印结果：1
              console.log("mock week:", week); // 打印结果：1
              // 确保 schoolId 和 week 不为 null
              if (schoolId && week) {
                const studentCourseData: Record<number, Record<number, { week: number; begin: number; length: number; students: string[] }[]>> = {
                  1: {
                    1: [
                      { week: 1, begin: 1, length: 2, students: ['张三', '李四'] },
                      { week: 1, begin: 3, length: 1, students: ['王五'] },
                    ],
                    2: [
                      { week: 2, begin: 3, length: 1, students: ['王五'] },
                    ]
                  },
                  2: {
                    2: [
                      { week: 2, begin: 1, length: 2, students: ['赵六', '钱七'] },
                      { week: 2, begin: 5, length: 2, students: ['钱七'] }
                    ],
                    3: [
                      { week: 3, begin: 2, length: 1, students: ['孙八'] },
                      { week: 3, begin: 7, length: 3, students: ['孙八', '钱七'] }
                    ]
                  },
                  3: {
                    3: [
                      { week: 3, begin: 1, length: 2, students: ['张榆', '李富贵'] },
                      { week: 3, begin: 3, length: 1, students: ['张榆'] },
                    ],
                    5: [
                      { week: 5, begin: 3, length: 1, students: ['李富贵'] },
                    ]
                  },
                  4: {
                    4: [
                      { week: 4, begin: 1, length: 2, students: ['王麻子', '花花'] },
                      { week: 4, begin: 5, length: 2, students: ['花花'] }
                    ],
                    6: [
                      { week: 6, begin: 2, length: 1, students: ['王雨涵'] },
                      { week: 6, begin: 7, length: 3, students: ['王麻子', '张榆萱'] }
                    ]
                  }
                };

                // 根据学校ID和周次获取课程表
                const schoolCourses = studentCourseData[parseInt(schoolId)];
                if (schoolCourses && schoolCourses[parseInt(week)]) {
                  return {
                    status: true,
                    message: '成功',
                    data: schoolCourses[parseInt(week)],
                  };
                } else {
                  return {
                    status: false,
                    message: '未找到该学校在指定周次的课程安排',
                    data: [],
                  };
                }
              } else {
                return {
                  status: false,
                  message: '学校ID或周次参数无效',
                  data: [],
                };
              }
            }
          }
        ]
    }
}

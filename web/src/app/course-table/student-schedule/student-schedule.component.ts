import {Component, OnInit} from '@angular/core';
import {Term} from "../../entity/term";
import {TermService} from "../../service/term.service";
import {CourseService} from "../../service/course.service";
import {CourseInfo, WeeklySchedule} from "../../entity/course-info";
import {CommonService} from "../../service/common.service";

@Component({
  selector: 'app-student-schedule',
  templateUrl: './student-schedule.component.html',
  styleUrls: ['./student-schedule.component.css']
})
export class StudentScheduleComponent implements OnInit{
  days = ['1', '2', '3', '4', '5', '6', '7'];
  periods = [
    { time: 1 },
    { time: 2 },
    { time: 3 },
    { time: 4 },
    { time: 5 },
    { time: 6 },
    { time: 7 },
    { time: 8 },
    { time: 9 },
    { time: 10 },
    { time: 11 },
  ];
  termStatus: number | null = null;
  selectedWeek : number | null = null;
  term = {} as Term;
  // 用于保存课程表数据，初始化为一个空对象
  weeklySchedule: WeeklySchedule = {};
  allWeeks = [] as any;
  termMessage = {} as Term;

  constructor(private termService: TermService,
              private courseService: CourseService,
              private commonService: CommonService) {}

  ngOnInit(): void {
    this.getTermAndWeeks();
    this.checkTermActive();

  }

  checkTermActive() {
    this.termService.checkTerm().subscribe(response => {
      this.termStatus = response.data.status;
    })
  }

  // 计算当前周，用于默认选中
  getCurrentWeek(): void {
    const currentDate = new Date();
    const startDate = new Date(this.termMessage.startTime);
    const diffInTime = currentDate.getTime() - startDate.getTime();
    const diffInDays = diffInTime / (1000 * 60 * 60 * 24);
    // 将当前周设为默认
    this.selectedWeek = Math.ceil(diffInDays / 7);
  }

  getTermAndWeeks() {
    this.termService.getTermAndWeeks().subscribe(response => {
      if (!response.status) {
        this.commonService.showErrorAlert(response.message);
      } else {
        const AllData = response.data;
        this.allWeeks = AllData.weeks;
        this.termMessage = AllData.term;
        // 获取到 term ，开始计算当前周
        this.getCurrentWeek();
        this.getCourseInfoByStudent();
      }
    })
  }

  getCourseInfoByStudent() {
    // if (this.selectedWeek !== null) {
    //   this.courseService.getCourseInfoByStudent(this.selectedWeek).subscribe(
    //     data => {
    //       if (data.status) {
    //         console.log(data);
    //         this.weeklySchedule = this.convertToWeeklySchedule(data.data);
    //       } else {
    //         alert(data.message);
    //       }
    //     }
    //   );
    // }
  }

  getClassInfo(day: any, time: number): CourseInfo | null {
    return this.weeklySchedule[day]?.[time] || null;
  }

  // 判断是否在当前时间段显示单元格
  shouldShowCell(day: any, time: number): boolean {
    const classInfo = this.getClassInfo(day, time);
    // 仅在课程的开始时间显示单元格
    return !classInfo || classInfo.begin === time;
  }

  // 将从后端获得的数据转换为 WeeklySchedule 类型
  convertToWeeklySchedule(data: any): WeeklySchedule {
    const weeklySchedule: WeeklySchedule = {};

    // 遍历课程数据 (遍历 courseId)
    Object.keys(data).forEach((courseId) => {
      const courseData = data[courseId];

      // 遍历课程的每一天 (day)
      Object.keys(courseData).forEach((day) => {
        const dayData = courseData[day];

        // 如果该课程的 day 没有初始化，则初始化
        if (!weeklySchedule[Number(day)]) {
          weeklySchedule[Number(day)] = {};
        }

        // 获取对应的 daySchedule
        const daySchedule = weeklySchedule[Number(day)];

        // 遍历每个时间段 (课程安排)
        dayData.forEach((item: any) => {
          const courseInfo: CourseInfo = {
            courseName: item.name,  // 可以根据需要设置课程名
            begin: item.begin,                 // 课程开始时间
            length: item.length                // 课程长度（总小节数）
          };

          // 将课程信息填入对应的 daySchedule 中
          for (let time = item.begin; time < item.begin + item.length; time++) {
            daySchedule[time] = courseInfo;
          }
        });
      });
    });
    return weeklySchedule;
  }

}

import {Component, OnInit} from '@angular/core';
import {Term} from "../../entity/term";
import {CourseInfo, WeeklySchedule} from "../../entity/course-info";
import {HttpParams} from "@angular/common/http";
import {CourseService} from "../../service/course.service";
import {TermService} from "../../service/term.service";
import {CommonService} from "../../service/common.service";

@Component({
  selector: 'app-admin-schedule',
  templateUrl: './admin-schedule.component.html',
  styleUrls: ['./admin-schedule.component.css']
})
export class AdminScheduleComponent implements OnInit{
  selectedSchool: number | null = null;
  allWeeks: number[] = [];
  termMessage = {} as Term;
  // 记录被选中的周数
  selectedWeek: number | null = null;
  schedule: WeeklySchedule = {};
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

  constructor(private courseService: CourseService,
              private termService: TermService,
              private commonService: CommonService) {}

  ngOnInit(): void {
  }

  getClassInfo(day: any, time: number): CourseInfo | null {
    return this.schedule[day]?.[time] || null;
  }

  // 判断是否在当前时间段显示单元格
  shouldShowCell(day: any, time: number): boolean {
    const classInfo = this.getClassInfo(day, time);
    // 仅在课程的开始时间显示单元格
    return !classInfo || classInfo.begin === time;
  }

  // 根据学校和周数，获取该学校所有学生的有课时间表
  getAllStudentsCourse() {
    if (this.selectedSchool !== null && this.selectedWeek !== null) {
      const params = new HttpParams()
        .append('schoolId', this.selectedSchool.toString())
        .append('week', this.selectedWeek.toString());

      this.courseService.getAllStudentsCourse(params).subscribe(
        data => {
          console.log(data.data);
          this.schedule = this.convertToWeeklySchedule(data.data);
        }
      );
    } else {
      console.error("School or week is null");
    }
  }

  onSchoolChange(schoolId: number) {
    // 当学校被重新选择之后，清空周数选择器的内容
    this.allWeeks = [];
    this.selectedWeek = null; // 清空选中的周数

    this.termService.getTermAndWeeksBySchoolId(schoolId).subscribe(
      responseBody => {
        if (!responseBody.status) {
          this.commonService.showErrorAlert(responseBody.message);
        } else {
          const AllData = responseBody.data;
          this.allWeeks = AllData.weeks;
          this.termMessage = AllData.term;
        }
      }
    );
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
            courseName: `课程名-${courseId}`,  // 可以根据需要设置课程名
            startWeek: item.week,              // 当前课程的周次
            endWeek: item.week,                // 可以根据需要调整 endWeek
            status: this.getStatus(item.week), // 获取课程的状态 (单双周或全周)
            students: item.students,           // 学生名单
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

// 获取课程状态 (单双周或全周)
  getStatus(week: number): string {
    if (week % 2 === 0) {
      return '双周';  // 双周
    } else if (week % 2 === 1) {
      return '单周';  // 单周
    } else {
      return '全周';  // 全周
    }
  }
}

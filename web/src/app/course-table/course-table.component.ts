import {Component, OnInit} from '@angular/core';
import {CourseInfo, WeeklySchedule} from "../entity/course-info";
import {Term} from "../entity/term";
import {Student} from "../entity/student";
import {CourseService} from "../service/course.service";
import {CommonService} from "../service/common.service";
import {TermService} from "../service/term.service";
import {HttpParams} from "@angular/common/http";

@Component({
  selector: 'app-course-table',
  templateUrl: './course-table.component.html',
  styleUrls: ['./course-table.component.css']
})
export class CourseTableComponent implements OnInit{
  selectedSchool: number | null = null;
  allWeeks: number[] = [];
  allStudents: Student[] = [];
  termMessage = {} as Term;
  // 记录被选中的周数
  selectedWeek: number | null = null;
  // 记录被选中的学生
  selectedStudent: number | null = null;
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

  // 根据学校和周数，获取该学校所有学生的有课时间表。加上studentId作为参数，获取指定学生的有课时间表
  getCourseMessage() {
    if (this.selectedSchool !== null && this.selectedWeek !== null) {
      let params = new HttpParams()
        .append('schoolId', this.selectedSchool.toString())
        .append('week', this.selectedWeek.toString());

      if (this.selectedStudent !== null) {
        params = params.append('studentId', this.selectedStudent.toString());
      }

      this.courseService.getCourseMessage(params).subscribe(
        data => {
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
    if (schoolId !== null) {
      this.termService.getTermAndWeeksAndStudentsBySchoolId(schoolId).subscribe(
        responseBody => {
          if (!responseBody.status) {
            this.commonService.showErrorAlert(responseBody.message);
          } else {
            const AllData = responseBody.data;
            this.allWeeks = AllData.weeks;
            this.termMessage = AllData.term;
            this.allStudents = AllData.students;
            this.getCurrentWeek();
          }
        }
      );
    } else {
      console.error("schoolId 为 null");
    }

  }

  // 将从后端获得的数据转换为 WeeklySchedule 类型
  convertToWeeklySchedule(data: any): WeeklySchedule {
    const weeklySchedule: WeeklySchedule = {};

    // 遍历 data 的每个键（周几）
    Object.keys(data).forEach((dayKey) => {
      const day = Number(dayKey);
      const dayData = data[day]; // 获取该天的课程数据

      // 如果该天的课表尚未初始化，则初始化为空对象
      if (!weeklySchedule[day]) {
        weeklySchedule[day] = {};
      }

      // 获取当天的课表
      const daySchedule = weeklySchedule[day];

      // 遍历该天的每一条课程数据
      dayData.forEach((item: any) => {
        const { begin, length, students } = item;

        // 将课程信息填入对应的时间段
        for (let time = begin; time < begin + length; time++) {
          daySchedule[time] = {
            begin,
            length,
            students,
          };
        }
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

  // 计算当前周，用于默认选中
  getCurrentWeek(): void {
    const currentDate = new Date();
    const startDate = new Date(this.termMessage.startTime);
    const diffInTime = currentDate.getTime() - startDate.getTime();
    const diffInDays = diffInTime / (1000 * 60 * 60 * 24);
    // 将当前周设为默认
    this.selectedWeek = Math.ceil(diffInDays / 7);
  }
}

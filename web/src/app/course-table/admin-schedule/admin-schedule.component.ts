import {Component, OnInit} from '@angular/core';
import {Term} from "../../entity/term";
import {CourseInfo, WeeklySchedule} from "../../entity/course-info";
import {HttpParams} from "@angular/common/http";
import {CourseService} from "../../service/course.service";
import {TermService} from "../../service/term.service";

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
              private termService: TermService) {}

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

    this.termService.getTermAndWeeks(schoolId).subscribe(
      responseBody => {
        if (!responseBody.status) {
          console.log(responseBody.message);
        } else {
          const AllData = responseBody.data;
          this.allWeeks = AllData.weeks;
          this.termMessage = AllData.term;
        }
      }
    );
  }

  // 将从后端获得的课程安排数据转换为 WeeklySchedule 类型
  convertToWeeklySchedule(data: any[]): WeeklySchedule {
    const weeklySchedule: WeeklySchedule = {};

    data.forEach(item => {
      const day = item.week; // week 表示周几
      const begin = item.begin; // begin 表示开始节数
      const length = item.length; // length 表示持续节数

      // 初始化 daySchedule 如果不存在
      if (!weeklySchedule[day]) {
        weeklySchedule[day] = {};
      }

      const daySchedule = weeklySchedule[day];

      // 填充指定的小节数
      for (let i = 0; i < length; i++) {
        const time = begin + i;
        daySchedule[time] = {
          students: item.students,
          begin: item.begin,
          length: item.length,
        };
      }
    });
    return weeklySchedule;
  }
}

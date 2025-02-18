import {Component, OnInit} from '@angular/core';
import {schoolIdAndWeeks} from "../../entity/schoolIdAndWeeks";
import {CourseInfo, WeeklySchedule} from "../../entity/course-info";
import {TermService} from "../../service/term.service";
import {CourseService} from "../../service/course.service";

@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.css']
})
export class AdminDashboardComponent implements OnInit{
  schoolIdAndStartTimeData: any[] = [];
  schoolIdAndWeeksData: schoolIdAndWeeks[] = [];
  schedule: WeeklySchedule = {};
  schoolDisplayData: any[] = [];
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

  constructor(private termService: TermService,
              private courseService: CourseService) {
  }

  ngOnInit(): void {
    this.getAllSchoolIdsAndStartTime();
  }

  // 计算每个学校的当前周数
  calculateSchoolWeeks(schoolIdAndStartTimeData: any[]) {
    const currentDate = new Date();  // 获取当前日期

    this.schoolIdAndWeeksData = schoolIdAndStartTimeData.map(school => {
      const startDate = new Date(school.startTime);  // 开学日期转为Date对象

      // 计算当前日期与开学日期之间的天数差
      const daysBetween = Math.floor((currentDate.getTime() - startDate.getTime()) / (1000 * 3600 * 24));

      // 计算当前是第几周
      const weeks = Math.floor(daysBetween / 7) + 1;

      return { schoolId: school.schoolId, weeks, name: school.name };  // 返回学校ID和当前周数
    });
  }

  getAllSchoolIdsAndStartTime() {
    this.termService.getAllSchoolIdsAndStartTime().subscribe(response => {
      if (response.status){
        this.schoolIdAndStartTimeData = response.data;
        this.calculateSchoolWeeks(this.schoolIdAndStartTimeData);
        this.getAllCourseInfo();
        this.mergeSchoolData();
      }
    });
  }

  getAllCourseInfo() {
    // this.courseService.getAllCourseInfo(this.schoolIdAndWeeksData).subscribe(response => {
    //   if (response.status){
    //     this.schedule = this.convertToWeeklySchedule(response.data);
    //   }
    // });
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

  getClassInfo(day: any, time: number): CourseInfo | null {
    return this.schedule[day]?.[time] || null;
  }

  // 判断是否在当前时间段显示单元格
  shouldShowCell(day: any, time: number): boolean {
    const classInfo = this.getClassInfo(day, time);
    // 仅在课程的开始时间显示单元格
    return !classInfo || classInfo.begin === time;
  }

  mergeSchoolData() {
    this.schoolDisplayData = this.schoolIdAndWeeksData.map((weekData) => {
      const school = this.schoolIdAndStartTimeData.find(item => item.schoolId === weekData.schoolId);

      return school ? {
        schoolId: school.schoolId,
        name: school.name,
        week: weekData.weeks
      } : null;
    }).filter(item => item !== null);
  }
}

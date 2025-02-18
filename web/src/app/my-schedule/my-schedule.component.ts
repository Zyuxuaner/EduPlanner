import {Component, OnInit} from '@angular/core';
import {CourseInfo, WeeklySchedule} from "../entity/course-info";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-my-schedule',
  templateUrl: './my-schedule.component.html',
  styleUrls: ['./my-schedule.component.css']
})
export class MyScheduleComponent implements OnInit {

  currentWeek: number | null = null;  // 当前周数
  totalWeeks: number = 0;   // 总周数
  days = [1, 2, 3, 4, 5, 6, 7];  // 星期数组
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
  ];  // 每天的时间段
  weeklySchedule: WeeklySchedule = {};
  termName: string = '';
  totalPeriodsPerDay = 11; // 每天的总节数
  availableLengths: number[] = []; // 可选的持续节数数组


  // 用于模态框的课程信息
  formGroup = new FormGroup({
    weeks: new FormControl([], [Validators.required]),
    name: new FormControl('', [Validators.required]),
    day: new FormControl(0, [Validators.required]),
    begin: new FormControl(0, [Validators.required]),
    length: new FormControl(1, [Validators.required]),
    weekType: new FormControl('', [Validators.required]),
  });

  allWeeks: number[] = [];

  constructor(private modalService: NgbModal) {
  }

  ngOnInit(): void {
    this.termName = 'tute2024-2025';
    this.totalWeeks = 18;  // 假设一学期有18周
    this.currentWeek = 1;  // 默认为当前周
    this.initializeCourses();  // 初始化课表数据
  }

  // 判断是否能添加课程
  canAddCourse(day: any, slot: number) {
    const courseInfo = this.getClassInfo(day, slot);
    return !courseInfo;
  }

  covertToWeeklySchedule(data: any): WeeklySchedule {
    const weeklySchedule: WeeklySchedule = {};

    // 遍历课程数据
    Object.keys(data).forEach((courseId) => {
      const courseData = data[courseId];

      // 遍历课程的每一天(day)
      Object.keys(courseData).forEach((day) => {
        const dayData = courseData[day];

        // 如果该课程的 day ，没有初始化，则初始化
        if (!weeklySchedule[Number(day)]) {
          weeklySchedule[Number(day)] = {};
        }

        // 获取对应的 daySchedule
        const daySchedule = weeklySchedule[Number(day)];

        // 遍历课程的每个时间段(课程安排)
        dayData.forEach((item: any) => {
          const courseInfo: CourseInfo = {
            courseName: item.name,
            begin: item.begin,
            length: item.length,
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

  changeWeek(number: number) {
    if (this.currentWeek !== null) {
      const newWeek = this.currentWeek + number;
      // @ts-ignore
      if (newWeek >= 1 && newWeek <= this.totalWeeks) {
        this.currentWeek = newWeek;
      }
    }
  }

  getCourseColor(day: any, time: number): string {
    const courseInfo = this.getClassInfo(day, time);
    return courseInfo ? this.getColorForCourse(courseInfo) : '';
  }

  getColorForCourse(course: CourseInfo): string {
    const colors = ['#FFDDC1', '#FFABAB', '#FFC3A0', '#FF677D', '#D4A5A5']; // 颜色池
    // @ts-ignore
    return colors[course.begin % colors.length]; // 根据课程的日期分配颜色
  }

  // 获取某一天某节课的课程信息
  getClassInfo(day: any, time: number): CourseInfo | null {
    return this.weeklySchedule[day]?.[time] || null;
  }

  // 初始化课表数据
  initializeCourses() {
    // 启用服务获取后台数据
    // 假设有一些课程数据（模拟的是从后台获取到的数据）
    const data = {
      1: { 4: [{name: '离散数学', begin: 4, length: 2, weeks: [1, 2], weekType: '双'}]},
      2: { 3: [{name: '大学物理', begin: 3, length: 3, weeks: [1, 2, 3], weekType: '全'}]},
      3: { 2: [{name: '数据库原理及其应用', begin: 5, length: 2, weeks: [1, 2], weekType: '双'}]},
      4: { 1: [{name: '大学英语', begin: 1, length: 2, weeks: [1, 2], weekType: '双'}]},
      5: { 2: [{name: 'C语言', begin: 2, length: 2, weeks: [1, 2], weekType: '双'}]},
    }
    this.weeklySchedule = this.covertToWeeklySchedule(data);
    this.allWeeks = Array.from({length: this.totalWeeks!}, (_, index) => index + 1);
  }

  // 打开模态框并根据选择的单元格设置 day 和 begin 的默认值
  openAddCourseModal(day: number, time: number) {
    this.formGroup.patchValue({
      day:day,
      begin: time
    });
    this.updateAvailableLengths(time);
  }

  onSubmit() {
    const result = this.formGroup.value;
    console.log('result:', result);
  }

  // 处理子组件（week-selector）传来的数据
  onWeekSelectionChange(event: { weeks: number[], weekType: 'any' | 'all' | 'odd' | 'even' }) {
    this.formGroup.patchValue({
      // @ts-ignore
      weeks: event.weeks,
      weekType: event.weekType
    })
  }

  // 判断是否显示课程单元格
  shouldShowCell(day: any, time: number): boolean {
    const classInfo = this.getClassInfo(day, time);
    // 仅在课程的开始时间显示单元格
    return !classInfo || classInfo.begin === time;
  }

  // 选择了某个单元格时更新可选持续节数
  updateAvailableLengths(begin: number) {
    const remainingPeriods = this.periods.length - begin + 1;
    this.availableLengths = Array.from({ length: remainingPeriods }, (_, i) => i + 1);
  }
}

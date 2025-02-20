import {Component, OnInit} from '@angular/core';
import {CourseInfo, WeeklySchedule} from "../entity/course-info";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {CourseService} from "../service/course.service";
import {SaveRequest} from "../dto/courseDto/saveRequest";
import {CommonService} from "../service/common.service";
import {LoginService} from "../service/login.service";
import {HttpParams} from "@angular/common/http";
import {TermService} from "../service/term.service";

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
  currentStudentId: number = 0; // 当前登录用户的学生id
  currentSchoolId: number = 0; // 当前登录用户的学校id


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

  constructor(private courseService: CourseService,
              private commonService: CommonService,
              private loginService: LoginService,
              private termService: TermService) {
  }

  ngOnInit(): void {
    // 成功获取到了当前登录用户的信息之后再调用getTerm方法
    this.setId().then(() => {
      this.getTerm();
    });
  }

  // 判断是否能添加课程
  canAddCourse(day: any, slot: number) {
    const courseInfo = this.getClassInfo(day, slot);
    return !courseInfo;
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
        const { begin, length, students, courseName } = item;

        // 将课程信息填入对应的时间段
        for (let time = begin; time < begin + length; time++) {
          daySchedule[time] = {
            courseName,
            begin,
            length,
            students,
          };
        }
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
        this.getCourseMessage(this.currentWeek);
      }
    }
  }

  getCourseMessage(week: number) {
    const params = new HttpParams()
      .append('schoolId', this.currentSchoolId.toString())
      .append('week', week.toString())
      .append('studentId', this.currentStudentId.toString());
    this.courseService.getCourseMessage(params).subscribe(data => {
      if (data.status) {
        this.weeklySchedule = this.convertToWeeklySchedule(data.data);
      }
    });
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

  getTerm(): void {
    this.termService.getTermAndWeeks().subscribe(response => {
      if (response.status) {
        const termMessage = response.data;
        if (termMessage.weeks && termMessage.weeks.length > 0) {
          this.termName = termMessage.term.name;
          this.totalWeeks = termMessage.weeks.length;
          this.allWeeks = Array.from({length: this.totalWeeks!}, (_, index) => index + 1);

          const currentDate = new Date();
          const startDate = new Date(termMessage.term.startTime);
          const diffInTime = currentDate.getTime() - startDate.getTime();
          const diffInDays = diffInTime / (1000 * 60 * 60 * 24);
          // 将当前周设为默认
          this.currentWeek = Math.ceil(diffInDays / 7);
          this.getCourseMessage(this.currentWeek);
        } else {
          console.log("无效的周数");
        }
      }

    });
  }

  // 打开模态框并根据选择的单元格设置 day 和 begin 的默认值
  openAddCourseModal(day: number, time: number) {
    this.formGroup.reset({
      weeks: [],
      name: '',
      day: day,
      begin: time,
      length: 1,
      weekType: '',
    });
    this.updateAvailableLengths(time);
  }

  onSubmit() {
    const result = this.formGroup.value as SaveRequest;
    this.courseService.add(result).subscribe(
      (response) => {
        if (response.status) {
          // 关闭模态框
          ($('#addCourseModal') as any).modal('hide');
          this.getTerm();
        } else {
          this.commonService.showErrorAlert(response.message);
        }
      }
    );
  }

  // 处理子组件（week-selector）传来的数据
  onWeekSelectionChange(event: { weeks: number[], weekType: 'other' | 'all' | 'odd' | 'even' }) {
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

  // 设置当前登录用户id以及当前登录用户的学校id
  setId(): Promise<void> {
    return new Promise((resolve, reject) => {
      this.loginService.getCurrentStudent().subscribe(
        data => {
          const currentStudent = data.data;
          this.currentStudentId = currentStudent.id;
          this.currentSchoolId = currentStudent.school.id;
          resolve();
        },
        error => {
          console.error('获取当前的登录用户信息失败', error);
          reject(error);
        }
      );
    });
  }
}

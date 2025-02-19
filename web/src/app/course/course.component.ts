import {Component, OnInit} from '@angular/core';
import {Course} from "../entity/course";
import {FormControl, FormGroup} from "@angular/forms";
import {CourseService} from "../service/course.service";
import {Router} from "@angular/router";
import {TermService} from "../service/term.service";
import {CommonService} from "../service/common.service";
import {GetAllResponse} from "../dto/courseDto/getAllResponse";
import {LoginService} from "../service/login.service";

@Component({
  selector: 'app-course',
  templateUrl: './course.component.html',
  styleUrls: ['./course.component.css']
})
export class CourseComponent implements OnInit{
  courses = [] as GetAllResponse[];
  formGroup = new FormGroup({
    searchCourse: new FormControl(null),
    type: new FormControl(''),
  });
  currentStudentId: number = 0;
  termId: number | null = null;

  constructor(private courseService: CourseService,
              private termService: TermService,
              private commonService: CommonService,
              private loginService: LoginService,
              private router: Router){}

  ngOnInit(): void {
      this.setCurrentStudentId();
      this.courseService.getAll().subscribe(courses => {
        this.courses = courses;
        this.getActiveTerm();
      });
  }

  // 获取当前登录用户的所在学校的激活学期
  getActiveTerm() {
    this.termService.checkTerm().subscribe(termResponse => {
      if (termResponse.status) {
        const termData = termResponse.data;
        this.termId = termData.id;
      }
    });
  }

  getWeekday(week: number): string {
    const days = ['星期一', '星期二', '星期三', '星期四', '星期五', '星期六', '星期天'];
    return days[week - 1];
  }

  getRange(begin: number, length: number): string {
    return `${begin} - ${begin + length - 1} 节`;
  }

  // 计算并返回周次范围
  getWeeks(startWeek: number, endWeek: number, weekType: number): string {
    let weekTypeStr = '';
    if (weekType === 3) {
      weekTypeStr = '全';
    } else if (weekType === 2) {
      weekTypeStr = '双';
    } else if (weekType === 1) {
      weekTypeStr = '单';
    }
    return `${startWeek} - ${endWeek}周 ${weekTypeStr}`;
  }

  onSearch() {
  }

  onDelete(courseInfoId: any) {

  }

  onEdit(id: number, courseInfoId: number | undefined) {

  }

  setCurrentStudentId() {
    this.loginService.getCurrentStudent().subscribe(data => {
      const currentStudent = data.data;
      this.currentStudentId = currentStudent.id;
    })
  }

  // 判断课程是否已被当前学生复用
  isCourseReused(courseInfo: number): boolean {
    const course = this.courses.find(course => course.courseInfo.id === courseInfo) as GetAllResponse;
    return course && course.reuseStudents && course.reuseStudents.some(student => student.id === this.currentStudentId);
  }

  reload() {
    this.courseService.getAll().subscribe(courses => {
      this.courses = courses;
    });
  }

  // 复用课程
  reuseCourse(courseInfo: number) {
    this.courseService.reuse(courseInfo).subscribe(data => {
      if (data.status) {
        this.commonService.showSuccessAlert(data.message);
        this.reload();
      } else {
        this.commonService.showErrorAlert(data.message);
      }
    })
  }

  // 取消复用
  cancelReuse(courseInfo: number) {
    this.courseService.cancelReuse(courseInfo).subscribe(data => {
      if (data.status) {
        this.commonService.showSuccessAlert(data.message);
        this.reload();
      } else {
        this.commonService.showErrorAlert(data.message);
      }
    })
  }

}

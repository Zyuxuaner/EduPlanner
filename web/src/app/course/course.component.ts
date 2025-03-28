import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {CourseService} from "../service/course.service";
import {TermService} from "../service/term.service";
import {CommonService} from "../service/common.service";
import {GetAllResponse} from "../dto/courseDto/getAllResponse";
import {LoginService} from "../service/login.service";
import {Student} from "../entity/student";
import {StudentService} from "../service/student.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-course',
  templateUrl: './course.component.html',
  styleUrls: ['./course.component.css']
})
export class CourseComponent implements OnInit{
  courses = [] as GetAllResponse[];
  formGroup: FormGroup;
  currentStudentId: number = 0;
  termId: number | null = null;
  allStudents: Student[] = [];

  constructor(private courseService: CourseService,
              private termService: TermService,
              private commonService: CommonService,
              private loginService: LoginService,
              private studentService: StudentService,
              private router: Router,
              private route: ActivatedRoute){
    this.formGroup = new FormGroup({
      searchCourse: new FormControl(null),
      creatorStudent: new FormControl('')
    });
  }

  ngOnInit(): void {
      this.setCurrentStudentId();
      this.courseService.getAll().subscribe(courses => {
        this.courses = courses;
        this.getActiveTerm();
      });
      this.studentService.getAll().subscribe(data => {
        this.allStudents = data;
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
    const { searchCourse, creatorStudent } = this.formGroup.value;
    console.log(searchCourse, creatorStudent);
    this.courseService.search(searchCourse, creatorStudent).subscribe((data) => {
      this.courses = data;
    })
  }

  onDelete(courseInfoId: any) {
    this.courseService.delete(courseInfoId).subscribe(data => {
      if (data.status) {
        this.commonService.showSuccessAlert(data.message);
        this.reload();
      } else {
        this.commonService.showErrorAlert(data.message);
      }
    });
  }

  onEdit(courseInfo: number) {
    this.courseService.checkReuse(courseInfo).subscribe(result => {
      if (result.status) {
        this.router.navigate(['edit', courseInfo], { relativeTo: this.route });
      } else {
        const message = result.message + '，不允许编辑';
        this.commonService.showErrorAlert(message);
      }
    });
  }

  setCurrentStudentId() {
    this.loginService.getCurrentStudent().subscribe(data => {
      const currentStudent = data.data;
      this.currentStudentId = currentStudent.id;
    })
  }

  // 判断课程是否已被当前学生复用，true（已复用）,false（未复用）
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

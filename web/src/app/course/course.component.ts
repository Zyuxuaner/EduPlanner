import {Component, OnInit} from '@angular/core';
import {Course} from "../entity/course";
import {FormControl, FormGroup} from "@angular/forms";
import {CourseService} from "../service/course.service";
import {Router} from "@angular/router";
import {TermService} from "../service/term.service";

@Component({
  selector: 'app-course',
  templateUrl: './course.component.html',
  styleUrls: ['./course.component.css']
})
export class CourseComponent implements OnInit{
  courses = [] as Course[];
  formGroup = new FormGroup({
    searchCourse: new FormControl(null),
    type: new FormControl(''),
  });
  termId: number | null = null;

  constructor(private courseService: CourseService,
              private termService: TermService,
              private router: Router){}

  ngOnInit(): void {
      this.courseService.getAll().subscribe(courses => {
        // 测试结课显示
        this.termId = 1;
        this.courses = courses;
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
  getWeeks(start_week: number, end_week: number, weekType: number): string {
    let weekTypeStr = '';
    if (weekType === 3) {
      weekTypeStr = '全';
    } else if (weekType === 2) {
      weekTypeStr = '双';
    } else if (weekType === 1) {
      weekTypeStr = '单';
    }
    return `${start_week} - ${end_week}周 ${weekTypeStr}`;
  }

  onSearch() {
  }

  onDelete(courseInfoId: any) {

  }

  onEdit(id: number, courseInfoId: number | undefined) {

  }

  // 如果该用户下没有激活的学期，不进行页面跳转并提示用户
  checkBeforeAdd(): void {
    this.termService.checkTerm().subscribe(data => {
      console.log(data);
      if (data.status) {
        this.router.navigate(['/course/add']);
      } else {
        alert(data.message);
      }
    });
  }
}

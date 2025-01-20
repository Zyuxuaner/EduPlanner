import {Component, OnInit} from '@angular/core';
import {Course} from "../entity/course";
import {FormControl, FormGroup} from "@angular/forms";
import {CourseService} from "../service/course.service";

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

  constructor(private courseService: CourseService,) {}

  ngOnInit(): void {
      this.courseService.getAll().subscribe(courses => {
        // 测试结课显示
        this.termId = 1;
        this.courses = courses;
      });
  }

  onSearch() {
  }

  onDelete(courseInfoId: any) {

  }

  onEdit(id: number, courseInfoId: number) {

  }

  checkBeforeAdd() {

  }
}

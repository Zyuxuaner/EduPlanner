import {Component, OnInit} from '@angular/core';
import {Student} from "../entity/student";
import {StudentService} from "../service/student.service";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {LoginService} from "../service/login.service";

@Component({
  selector: 'app-student',
  templateUrl: './student.component.html',
  styleUrls: ['./student.component.css']
})
export class StudentComponent implements OnInit {
  schoolId: number | null = null;
  formGroup: FormGroup = new FormGroup({
    schoolId: new FormControl(),
    clazzId: new FormControl(),
    searchName: new FormControl(),
    searchStudentSno: new FormControl()
  });
  searchName = '';
  searchStudentSno = '';
  students: Student[] = [];

  constructor(private studentService: StudentService,
              private loginService: LoginService) {
  }

  ngOnInit(): void {
    this.getAll();
  }

  getAll(): void {
    this.studentService.getAll().subscribe(data => {
      this.students = data;
    });
  }

  // 当学校选择变化时，更新 schoolId
  onSchoolChange(schoolId: number): void {
    this.schoolId = schoolId;
    console.log(this.schoolId);
  }

  onEdit(id: number): void {}

  onActive(id: number): void {}

  onSearch() {}

  onDelete(id: number): void {}
}

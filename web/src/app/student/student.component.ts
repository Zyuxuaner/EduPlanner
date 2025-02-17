import {Component, OnInit} from '@angular/core';
import {Student} from "../entity/student";
import {StudentService} from "../service/student.service";
import {FormControl, FormGroup} from "@angular/forms";
import {CommonService} from "../service/common.service";
import {ActivatedRoute, Router} from "@angular/router";
import {SchoolImpl} from "../entity/school";

@Component({
  selector: 'app-student',
  templateUrl: './student.component.html',
  styleUrls: ['./student.component.css']
})
export class StudentComponent implements OnInit {
  selectedSchool: SchoolImpl | null = null;
  formGroup: FormGroup = new FormGroup({
    schoolId: new FormControl(),
    searchName: new FormControl(),
    searchStudentSno: new FormControl()
  });
  students: Student[] = [];

  constructor(private studentService: StudentService,
              private router: Router,
              private commonService: CommonService,
              private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.getAll();
  }

  getAll(): void {
    this.studentService.getAll().subscribe(data => {
      this.students = data;
    });
  }

  // 当学校选择变化时，更新 school
  onSchoolChange(school: SchoolImpl | null): void {
    this.selectedSchool = school;
  }

  onEdit(id: number): void {
    this.router.navigate(['edit', id], { relativeTo: this.route });
  }

  onActive(id: number, status: number): void {
    this.studentService.changeStatus(id, status).subscribe(() => {
      this.getAll();
    })
  }

  onResetPassword(id: number): void {
    const constPassword = "123456"
    this.studentService.resetPassword(id, constPassword).subscribe(response => {
      this.commonService.showSuccessAlert(response.message);
    })
  }

  onSearch(): void {
    const { schoolId, searchName, searchStudentSno } = this.formGroup.value;
    console.log(schoolId, searchName, searchStudentSno);
    this.studentService.search(schoolId, searchName, searchStudentSno).subscribe(data => {
      this.students = data;
    });
  }

  onDelete(id: number): void {
    this.studentService.delete(id).subscribe(response => {
      if (response.status) {
        this.commonService.showSuccessAlert(response.message);
        this.getAll();
      } else {
        this.commonService.showErrorAlert(response.message);
      }
    })
  }
}

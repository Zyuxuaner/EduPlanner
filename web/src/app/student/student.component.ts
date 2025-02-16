import {Component, OnInit} from '@angular/core';
import {Student} from "../entity/student";
import {StudentService} from "../service/student.service";
import {FormControl, FormGroup} from "@angular/forms";
import {CommonService} from "../service/common.service";
import {ActivatedRoute, Router} from "@angular/router";
import {School} from "../entity/school";

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

  // 当学校选择变化时，更新 schoolId
  onSchoolChange(schoolId: School): void {
    // this.schoolId = schoolId;
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
    const { schoolId, clazzId, searchName, searchStudentSno } = this.formGroup.value;
    this.studentService.search(schoolId, clazzId, searchName, searchStudentSno).subscribe(data => {
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

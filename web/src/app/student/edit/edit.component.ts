import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {StudentService} from "../../service/student.service";
import {ActivatedRoute, Router} from "@angular/router";
import {SchoolImpl} from "../../entity/school";
import {CommonService} from "../../service/common.service";

@Component({
  selector: 'app-edit',
  templateUrl: './edit.component.html',
  styleUrls: ['./edit.component.css']
})
export class EditComponent implements OnInit {
  selectedSchool: SchoolImpl | null = null;
  schoolId: number | null = null;
  formGroup: FormGroup;

  id = Number(this.route.snapshot.paramMap.get('id'));

  constructor(private studentService: StudentService, private route: ActivatedRoute, private router: Router,private commonService: CommonService) {
    this.formGroup = new FormGroup({
      school_id: new FormControl(null, Validators.required),
      name: new FormControl('', Validators.required),
      username: new FormControl('', Validators.required),
      sno: new FormControl(null, [Validators.required, Validators.minLength(6)])
    });
  }

  // 当学校选择变化时，更新 school
  onSchoolChange(school: SchoolImpl | null): void {
    this.selectedSchool = school;
  }

  ngOnInit(): void {
    this.studentService.getStudentById(this.id).subscribe(student => {
      if(this.formGroup) {
        this.formGroup.patchValue({
          school_id: student.school.id,
          name: student.name,
          sno: student.sno,
          username: student.user.username
        });
      }
      // 保存完整的 School 对象，用于提交时使用
      this.selectedSchool = student.school;
    })
  }

  onSubmit(): void {
    if (this.formGroup.valid) {
      const studentData = { ...this.formGroup.value, id: this.id };
      // 将 school_id 替换为完整的 School 对象
      if (this.selectedSchool) {
        studentData.school = this.selectedSchool;
        delete studentData.school_id;
      }
      this.studentService.updateStudent(studentData).subscribe(response => {
        if (response.status) {
          this.commonService.showSuccessAlert(response.message);
          this.router.navigate(['/student']);
        } else {
          this.commonService.showErrorAlert(response.message);
        }
      });
    }
  }
}

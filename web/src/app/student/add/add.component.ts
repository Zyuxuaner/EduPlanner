import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {SchoolImpl} from "../../entity/school";
import {StudentService} from "../../service/student.service";
import {Router} from "@angular/router";
import {CommonService} from "../../service/common.service";

@Component({
  selector: 'app-add',
  templateUrl: './add.component.html',
  styleUrls: ['./add.component.css']
})
export class AddComponent implements OnInit {
  selectedSchool: SchoolImpl | null = null;
  formGroup = new FormGroup({
    school_id: new FormControl<number | null>(null, Validators.required),
    name: new FormControl('', Validators.required),
    username: new FormControl('', Validators.required),
    sno: new FormControl(null, [Validators.required, Validators.minLength(6)])
  });

  ngOnInit(): void {
    console.log(this.formGroup.value);
  }

  constructor(private studentService: StudentService,
              private router: Router,
              private commonService: CommonService) {
  }

  onSubmit(): void {
    const formValue = this.formGroup.value;
    const school = this.selectedSchool;

    const student = {
      school: school!,
      name: formValue.name!,
      username: formValue.username!,
      sno: formValue.sno!,
    };
    console.log(student);
    this.studentService.add(student).subscribe(response => {
      if (response.status) {
        this.commonService.showSuccessAlert(response.message);
        this.router.navigate(['/student']);
      } else {
        this.commonService.showErrorAlert(response.message);
      }
    });
  }

  // 当学校选择变化时，更新 schoolId
  onSchoolChange(school: SchoolImpl): void {
    this.selectedSchool = school;
  }
}

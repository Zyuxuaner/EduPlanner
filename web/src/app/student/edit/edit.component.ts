import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {StudentService} from "../../service/student.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-edit',
  templateUrl: './edit.component.html',
  styleUrls: ['./edit.component.css']
})
export class EditComponent implements OnInit {
  schoolId: number | null = null;
  formGroup: FormGroup;

  id = Number(this.route.snapshot.paramMap.get('id'));

  constructor(private studentService: StudentService, private route: ActivatedRoute, private router: Router) {
    this.formGroup = new FormGroup({
      school_id: new FormControl(null, Validators.required),
      clazz_id: new FormControl(null, Validators.required),
      name: new FormControl('', Validators.required),
      username: new FormControl('', Validators.required),
      sno: new FormControl(null, [Validators.required, Validators.minLength(6)])
    });
  }

  // 当学校选择变化时，更新 schoolId
  onSchoolChange(schoolId: number): void {
    this.schoolId = schoolId;
  }

  ngOnInit(): void {
    this.studentService.getStudentById(this.id).subscribe(student => {
      if(this.formGroup) {
        this.formGroup.patchValue({
          school_id: student.clazz.school.id,
          clazz_id: student.clazz.id,
          name: student.name,
          sno: student.sno,
          username: student.user.username
        });
        console.log(student);
        this.onSchoolChange(student.clazz.school.id);
      }
    })
  }

  onSubmit(): void {}
}

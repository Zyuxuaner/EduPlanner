import { Component } from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {SchoolService} from "../service/school.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-school-add',
  templateUrl: './school-add.component.html',
  styleUrls: ['./school-add.component.css']
})
export class SchoolAddComponent {
  // 新增的学校
  formGroup = new FormGroup({
    name: new FormControl('', Validators.required),
  });

  constructor(private schoolService: SchoolService,private router: Router) { }

  onSubmit(): void {
    const addSchool = this.formGroup.value as { name: string | null };
    this.schoolService.add(addSchool).subscribe(data => {
      console.log(data);
      alert('添加成功');
      this.router.navigate(['/school']);
    })
  }
}

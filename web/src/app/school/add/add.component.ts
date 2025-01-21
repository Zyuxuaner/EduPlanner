import { Component } from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {SchoolService} from "../../service/school.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-add',
  templateUrl: './add.component.html',
  styleUrls: ['./add.component.css']
})
export class AddComponent {
  // 新增的学校
  formGroup = new FormGroup({
    name: new FormControl('', Validators.required),
  });

  constructor(private schoolService: SchoolService,private router: Router) { }

  onSubmit(): void {
    const addSchool = this.formGroup.value as { name: string | null };
    this.schoolService.add(addSchool).subscribe(data => {
      this.router.navigate(['/school']);
    })
  }

}

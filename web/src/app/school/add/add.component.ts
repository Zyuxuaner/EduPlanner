import { Component } from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {SchoolService} from "../../service/school.service";
import {Router} from "@angular/router";
import {CommonService} from "../../service/common.service";

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

  constructor(private schoolService: SchoolService,
              private router: Router,
              private commonService: CommonService) { }

  onSubmit(): void {
    const addSchool = this.formGroup.value as { name: string | null };
    this.schoolService.add(addSchool).subscribe(response => {
      if (response.status) {
        this.commonService.showSuccessAlert(response.message);
        this.router.navigate(['/school']);
      } else {
        this.commonService.showErrorAlert(response.message);
      }
    })
  }

}

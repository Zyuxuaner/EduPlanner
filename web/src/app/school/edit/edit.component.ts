import {Component, OnInit} from '@angular/core';
import {School} from "../../entity/school";
import {ActivatedRoute, Router} from "@angular/router";
import {SchoolService} from "../../service/school.service";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {CommonService} from "../../service/common.service";

@Component({
  selector: 'app-edit',
  templateUrl: './edit.component.html',
  styleUrls: ['./edit.component.css']
})
export class EditComponent implements OnInit {
  school: School | undefined;
  editFormGroup = new FormGroup({
    name: new FormControl('', Validators.required)
  });

  constructor(
    private route: ActivatedRoute,
    private schoolService: SchoolService,
    private router: Router,
    private commonService: CommonService
  ) {}

  ngOnInit(): void {
    const schoolId = Number(this.route.snapshot.paramMap.get('id'));
    console.log(this.route.snapshot.paramMap.get('id'));
    this.schoolService.getSchoolById(schoolId).subscribe(data => {
      this.school = data[0];
      // 在获取到学校信息后更新表单的值
      if (this.school) {
        this.editFormGroup.patchValue({
          name: this.school.name
        });
      }
    });
  }

  saveChanges(): void {
    if (this.school && this.editFormGroup.valid) {
      const name = this.editFormGroup.get('name')?.value;
      if (typeof name === 'string') {
        this.school.name = name;
        this.schoolService.updateSchool(this.school.id, this.school.name).subscribe(response => {
          if (response.status) {
            this.commonService.showSuccessAlert(response.message);
            this.router.navigate(['/school']);
          } else {
            this.commonService.showErrorAlert(response.message);
          }
        });
      }
    }
  }
}

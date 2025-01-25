import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {ClazzService} from "../../service/clazz.service";
import {Router} from "@angular/router";
import {CommonService} from "../../service/common.service";

@Component({
  selector: 'app-add',
  templateUrl: './add.component.html',
  styleUrls: ['./add.component.css']
})
export class AddComponent implements OnInit {
  formGroup = new FormGroup({
    clazz: new FormControl('', Validators.required),
    school_id: new FormControl(null, Validators.required)
  });

  constructor(private clazzService: ClazzService,
              private router: Router,
              private commonService: CommonService) { }

  ngOnInit(): void {
  }

  onSubmit(): void {
    const formValue = this.formGroup.value;
    const schoolId = formValue.school_id!;
    const clazz = {
      name: formValue.clazz!,
      school: {id: schoolId, name: 'undefined'}
    } as {name: string; school: {id: number, name: string}
    };
    this.clazzService.add(clazz).subscribe(response => {
      if (response.status) {
        this.commonService.showSuccessAlert(response.message);
        this.router.navigate(['/clazz']);
      } else {
        this.commonService.showErrorAlert(response.message);
      }});
  }
}

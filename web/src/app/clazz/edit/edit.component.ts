import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {ClazzService} from "../../service/clazz.service";
import {Clazz} from "../../entity/clazz";
import {CommonService} from "../../service/common.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-edit',
  templateUrl: './edit.component.html',
  styleUrls: ['./edit.component.css']
})
export class EditComponent implements OnInit {
  readonly clazzId = Number(this.route.snapshot.paramMap.get('clazzId'));

  editFormGroup = new FormGroup({
    school_id: new FormControl<number | null>(null, Validators.required),
    clazzName: new FormControl('', Validators.required),
  });

  constructor(private clazzService: ClazzService, private router: Router, private commonService: CommonService, private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.clazzService.getClazzByClazzId(this.clazzId).subscribe(data => {
      this.editFormGroup.patchValue({
        school_id: data.school.id,
        clazzName: data.name
      });
    });
  }

  onEditSubmit() {
    if (this.editFormGroup.valid) {
      const { school_id: schoolId, clazzName } = this.editFormGroup.value;
      // 调用更新班级的服务方法
      if (typeof schoolId === 'number' && typeof clazzName === 'string' && clazzName.trim()!== '') {
        this.clazzService.updateClazz(this.clazzId, schoolId, clazzName).subscribe(response => {
          if (response.status) {
            // 成功提示并导航到指定页面
            this.commonService.showSuccessAlert(response.message);
            this.router.navigate(['/clazz']);
          } else {
            // 失败提示
            this.commonService.showErrorAlert(response.message);
          }
        });
      }
    }
  }
}

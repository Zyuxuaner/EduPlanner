import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {TermService} from "../../service/term.service";
import {ActivatedRoute, Router} from "@angular/router";
import {CommonService} from "../../service/common.service";
import {SchoolImpl} from "../../entity/school";

@Component({
  selector: 'app-edit',
  templateUrl: './edit.component.html',
  styleUrls: ['./edit.component.css']
})
export class EditComponent implements OnInit {
  formGroup: FormGroup;
  selectedSchool: SchoolImpl | null = null;
  readonly id = Number(this.route.snapshot.paramMap.get('id'));

  constructor(
    private fb: FormBuilder,
    private termService: TermService,
    private route: ActivatedRoute,
    private router: Router,
    private commonService: CommonService
  ) {
    this.formGroup = this.fb.group({
      school_id: [null, Validators.required],
      name: [null, Validators.required],
      startTime: [null, Validators.required],
      endTime: [null, Validators.required]
    });
  }

  ngOnInit(): void {
    if (this.id) {
      this.loadTermData();
    }

    this.setupDateChangeSubscriptions();
  }

  private setupDateChangeSubscriptions(): void {
    this.formGroup.get('startTime')?.valueChanges.subscribe(() => this.onStartTimeChange());
    this.formGroup.get('endTime')?.valueChanges.subscribe(() => this.onEndTimeChange());
  }

  loadTermData(): void {
    this.termService.getTermById(this.id).subscribe(term => {
      if(this.formGroup) {
        this.formGroup.patchValue({
          school_id: term.school.id,
          name: term.name,
          startTime: term.startTime,
          endTime: term.endTime
        });
      }
      // 保存完整的 School 对象，用于提交时使用
      this.selectedSchool = term.school;
    });
  }

  onSubmit(): void {
    if (this.formGroup?.valid) {
      const termData = { ...this.formGroup.value, id: this.id };
      // 将 school_id 替换为完整的 School 对象
      if (this.selectedSchool) {
        termData.school = this.selectedSchool;
        delete termData.school_id;
      }
      this.termService.updateTerm(termData).subscribe(response => {
        if (response.status) {
          this.commonService.showSuccessAlert(response.message);
          this.router.navigate(['/term']);
        } else {
          this.commonService.showErrorAlert(response.message);
        }
      });
    }
  }

  // 当学校选择变化时，更新 school
  onSchoolChange(school: SchoolImpl | null): void {
    this.selectedSchool = school;
  }

  disableEndDate = (endDate: Date): boolean => {
    const startTime = this.formGroup.get('startTime')?.value;
    return startTime ? endDate <= startTime || this.disableNotMonday(endDate) : this.disableNotMonday(endDate);
  }

  disableStartTime = (startDate: Date): boolean => {
    const endTime = this.formGroup.get('endTime')?.value;
    return endTime ? startDate >= endTime || this.disableNotMonday(startDate) : this.disableNotMonday(startDate);
  }

  disableNotMonday = (current: Date): boolean => current.getDay()!== 1;

  onStartTimeChange(): void {
    const startTime = this.formGroup.get('startTime')?.value;
    const endTime = this.formGroup.get('endTime')?.value;
    if (startTime && endTime && startTime >= endTime) {
      // 只有当 startTime 大于等于 endTime 时才更新 endTime
      this.formGroup.get('endTime')?.updateValueAndValidity();
    }
  }

  onEndTimeChange(): void {
    const startTime = this.formGroup.get('startTime')?.value;
    const endTime = this.formGroup.get('endTime')?.value;
    if (startTime && endTime && endTime <= startTime) {
      // 只有当 endTime 小于等于 startTime 时才更新 startTime
      this.formGroup.get('startTime')?.updateValueAndValidity();
    }
  }
}

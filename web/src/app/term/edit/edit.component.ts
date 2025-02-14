import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {TermService} from "../../service/term.service";
import {ActivatedRoute, Router} from "@angular/router";
import {CommonService} from "../../service/common.service";

@Component({
  selector: 'app-edit',
  templateUrl: './edit.component.html',
  styleUrls: ['./edit.component.css']
})
export class EditComponent implements OnInit {
  formGroup: FormGroup;
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
    });
  }

  onSubmit(): void {
    if (this.formGroup?.valid) {
      const termData = { ...this.formGroup.value, id: this.id };
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
    this.formGroup.get('endTime')?.updateValueAndValidity();
  }

  onEndTimeChange(): void {
    this.formGroup.get('startTime')?.updateValueAndValidity();
  }
}

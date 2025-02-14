import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {School, SchoolImpl} from "../../entity/school";
import {TermService} from "../../service/term.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-add',
  templateUrl: './add.component.html',
  styleUrls: ['./add.component.css']
})
export class AddComponent implements OnInit {
  formGroup = new FormGroup({
    name: new FormControl('', Validators.required),
    startTime: new FormControl(null, Validators.required),
    endTime: new FormControl(null, Validators.required),
    school_id: new FormControl(null, Validators.required)
  });

  constructor(private termService: TermService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.setupDateChangeSubscriptions();
  }

  setupDateChangeSubscriptions(): void {
    this.formGroup.get('startTime')?.valueChanges.subscribe(() => this.onStartTimeChange());
    this.formGroup.get('endTime')?.valueChanges.subscribe(() => this.onEndTimeChange());
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

  onSubmit(): void {
    const formValue = this.formGroup.value;
    const schoolId = formValue.school_id!
    const term = {
      name: formValue.name!,
      startTime: new Date(formValue.startTime!).getTime(),
      endTime: new Date(formValue.endTime!).getTime(),
      school: new SchoolImpl(schoolId, 'undefined')
    } as {name: string; startTime: number; endTime: number; school: School};
    this.termService.add(term).subscribe(value => {
      this.router.navigate(['/term']);
    });
  }
}

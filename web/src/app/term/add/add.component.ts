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
  schools: School[] = [
    {id: 1, name: '天津职业技术师范大学'},
    {id: 2, name: '河北工业大学'}
  ];

  constructor(private termService: TermService,
              private router: Router) {
  }

  ngOnInit(): void {
  }

  disableEndDate = (endDate: Date): boolean => {
    const time = endDate.getTime();
    // @ts-ignore
    return this.formGroup.get('startTime')?.value.getTime() > time || this.disableNotMonday(endDate);
  }

  disableNotMonday = (current: Date): boolean => {
    return current.getDay() !== 1; // 1 表示星期一
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

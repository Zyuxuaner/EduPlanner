import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {CourseService} from "../../service/course.service";
import {Router} from "@angular/router";
import {TermService} from "../../service/term.service";
import {LoginService} from "../../service/login.service";
import {data} from "autoprefixer";
import {CommonService} from "../../service/common.service";

@Component({
  selector: 'app-add',
  templateUrl: './add.component.html',
  styleUrls: ['./add.component.css']
})
export class AddComponent implements OnInit{
  weekRange: number[] = [];

  // 节次
  sections: number[] = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11];
  termName: string = '';
  formGroup = new FormGroup({
    name: new FormControl('', Validators.required),
    type: new FormControl(0, Validators.required),
    status: new FormControl(0, Validators.required),
    startWeek: new FormControl(0, Validators.required),
    endWeek: new FormControl(0, Validators.required),
    week: new FormControl(0, Validators.required),
    begin: new FormControl(0, Validators.required),
    end: new FormControl(0, Validators.required),
  });

  constructor(private courseService: CourseService,
              private termService: TermService,
              private loginService: LoginService,
              private router: Router,
              private commonService: CommonService) {
  }

  ngOnInit(): void {
    const schoolId = 1;
      this.termService.getTermAndWeeks().subscribe(res => {
      this.termName = res.data.term.name;
      this.weekRange = res.data.weeks;
    });
  }

  // 禁用结束周小于开始周的选项
  disableEndWeek = (week: number): boolean => {
    const startWeek = this.formGroup.get('start_week')?.value;
    // @ts-ignore
    return startWeek && week < startWeek;
  };


  // 禁用结束节次小于开始节次的选项
  disableEndSection = (section: number): boolean => {
    const beginSection = this.formGroup.get('begin')?.value;
    // @ts-ignore
    return beginSection && section < beginSection;
  };


  isWeekDisabled(week: number): boolean {
    // 1：单；2：双；3：全
    const status = Number(this.formGroup.get('status')?.value);
    if (status === 1) {
      // 禁用双周
      return week % 2 === 0;
    }
    if (status === 2) {
      // 禁用单周
      return week % 2 !== 0;
    }
    // 全周，所有的都可用
    return false;
  }

  onSubmit(): void {
    // const course = this.formGroup.value as { name: string; type: number; status: number; startWeek: number; endWeek: number; week: number; begin: number; end: number };
    // console.log(course);
    // this.courseService.add(course).subscribe(data => {
    //   if (data.status) {
    //     this.commonService.showSuccessAlert(data.message);
    //     this.router.navigate(['/course']);
    //   } else {
    //     this.commonService.showErrorAlert(data.message);
    //   }
    // });
  }
}

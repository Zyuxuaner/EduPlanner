import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {CommonService} from "../../service/common.service";
import {CourseService} from "../../service/course.service";
import {GetByIdResponse} from "../../dto/courseDto/getByIdResponse";
import {SaveRequest} from "../../dto/courseDto/saveRequest";

@Component({
  selector: 'app-edit',
  templateUrl: './edit.component.html',
  styleUrls: ['./edit.component.css']
})
export class EditComponent implements OnInit{
  id = Number(this.route.snapshot.paramMap.get('id'));
  formGroup = new FormGroup({
    weeks: new FormControl([] as number [], [Validators.required]),
    name: new FormControl('', [Validators.required]),
    day: new FormControl(0, [Validators.required]),
    begin: new FormControl(0, [Validators.required]),
    length: new FormControl(1, [Validators.required]),
    weekType: new FormControl('', [Validators.required]),
  });
  totalWeeks: number = 0;
  termName: string = '2023-2024学年第一学期';
  availableLengths: number[] = [];
  periods = [1,2,3,4,5,6,7,8,9,10,11];
  days = [1,2,3,4,5,6,7];
  oldWeeks: number[] = [];
  oldWeekType: 'other' | 'all' | 'odd' | 'even' = 'all';

  constructor( private router: Router,
               private route: ActivatedRoute,
               private courseService: CourseService,
               private commonService: CommonService) {}

  ngOnInit(): void {
    this.courseService.getCourseInfoById(this.id).subscribe(res => {
      if (res.status) {
        const data = res.data as GetByIdResponse;
        this.formGroup.patchValue({
          weeks: data.courseInfo.weeks,
          name: data.name,
          day: data.courseInfo.day,
          begin: data.courseInfo.begin,
          length: data.courseInfo.length,
          weekType: data.courseInfo.weekType,
        });
        this.termName = data.term.name;
        this.totalWeeks = data.totalWeeks;
        this.oldWeeks = data.courseInfo.weeks;
        this.oldWeekType = data.courseInfo.weekType as 'other' | 'all' | 'odd' | 'even';
        this.updateAvailableLengths(data.courseInfo.begin);
      }
    });
  }

  updateAvailableLengths(begin: number) {
    // 11 是一天的总节数
    const remainingPeriods = 11 - begin + 1;
    this.availableLengths = Array.from({ length: remainingPeriods }, (_, i) => i + 1);
  }

  onSubmit() {
    const result = this.formGroup.value as SaveRequest;
    this.courseService.update(result, this.id).subscribe(res => {
      if (res.status) {
        this.router.navigate(['/course']);
      } else {
        this.commonService.showErrorAlert(res.message);
      }
    });
  }

  onWeekSelectionChange(event: { weeks: number[], weekType: 'other' | 'all' | 'odd' | 'even' }) {
    this.formGroup.patchValue({
      // @ts-ignore
      weeks: event.weeks,
      weekType: event.weekType
    })
  }

  onSelectChange(event: Event) {
    const selectedBegin = (event.target as HTMLSelectElement).value;
    this.updateAvailableLengths(Number(selectedBegin))
  }
}

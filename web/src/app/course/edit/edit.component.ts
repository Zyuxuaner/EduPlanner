import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {CommonService} from "../../service/common.service";
import {CourseService} from "../../service/course.service";
import {GetByIdResponse} from "../../dto/courseDto/getByIdResponse";

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
      }
    });
  }

  onSubmit() {

  }

  onWeekSelectionChange($event: { weeks: number[]; weekType: "other" | "all" | "odd" | "even" }) {

  }
}

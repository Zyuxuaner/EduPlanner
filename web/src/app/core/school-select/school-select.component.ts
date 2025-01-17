import {Component, EventEmitter, forwardRef, OnInit, Output} from '@angular/core';
import {ControlValueAccessor, FormControl, NG_VALUE_ACCESSOR} from "@angular/forms";
import {School} from "../../entity/school";
import {SchoolService} from "../../service/school.service";

@Component({
  selector: 'app-school-select',
  templateUrl: './school-select.component.html',
  styleUrls: ['./school-select.component.css'],
  providers: [{
    provide: NG_VALUE_ACCESSOR,
    useExisting: forwardRef(() => SchoolSelectComponent),
    multi: true
  }]
})
export class SchoolSelectComponent implements OnInit, ControlValueAccessor {
  schoolIdControl = new FormControl();
  schools: School[] = [];

  @Output() schoolIdChange = new EventEmitter<number>();

  constructor(private schoolService: SchoolService,) {
  }

  ngOnInit(): void {
    this.schoolService.getAll().subscribe(schools => {
      this.schools = schools;

      this.schoolIdControl.valueChanges.subscribe(value => {
        this.schoolIdChange.emit(value);
      });
    })
  }

  writeValue(schoolId: number): void {
      if (schoolId) {
          this.schoolIdControl.setValue(schoolId);
      }
  }

  registerOnChange(fn: (schoolId: number) => void): void {
    this.schoolIdControl.valueChanges.subscribe((value: number) => {
      fn(value);
    });
  }
  registerOnTouched(fn: any): void {
  }
  setDisabledState?(isDisabled: boolean): void {
  }
}

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
  schoolIdControl = new FormControl<number | null>(null);
  schools: School[] = [];

  @Output() schoolChange = new EventEmitter<School | null>();

  constructor(private schoolService: SchoolService) {
  }

  ngOnInit(): void {
    this.schoolService.getAll().subscribe(schools => {
      this.schools = schools;

      this.schoolIdControl.valueChanges.subscribe((id: number | null) => {
        const selectedSchool = id != null
          ? this.schools.find(school => school.id === id)
          : null;
        this.schoolChange.emit(selectedSchool ?? null);
      });
    });
  }

  writeValue(schoolId: number | null): void {
    this.schoolIdControl.setValue(schoolId);
  }

  registerOnChange(fn: (id: number | null) => void): void {
    this.schoolIdControl.valueChanges.subscribe(fn);
  }

  registerOnTouched(fn: any): void {
  }

  setDisabledState?(isDisabled: boolean): void {
    isDisabled ? this.schoolIdControl.disable() : this.schoolIdControl.enable();
  }
}

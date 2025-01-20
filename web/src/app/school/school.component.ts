import {Component, OnInit} from '@angular/core';
import {School} from "../entity/school";
import {Page} from "../entity/page";
import {HttpParams} from "@angular/common/http";
import {SchoolService} from "../service/school.service";

@Component({
  selector: 'app-school',
  templateUrl: './school.component.html',
  styleUrls: ['./school.component.css']
})
export class SchoolComponent implements OnInit {
  schools = [] as School[];
  searchName = '';

  ngOnInit(): void {
    console.log(2);
    this.getAll();
  }

  constructor(private schoolService:SchoolService,) {
  }

  getAll(): void {
    console.log(this.schools,1);
    this.schoolService.getAll().subscribe(data => {
      this.schools = data;
    });
  }

  onEdit(schoolsId: number): void {}

  onDelete(schoolsId: number): void {}

  onSearch(): void {}
}

import {Component, OnInit} from '@angular/core';
import {School} from "../entity/school";
import {SchoolService} from "../service/school.service";
import {ActivatedRoute, Router} from "@angular/router";
import {CommonService} from "../service/common.service";

@Component({
  selector: 'app-school',
  templateUrl: './school.component.html',
  styleUrls: ['./school.component.css']
})
export class SchoolComponent implements OnInit {
  schools = [] as School[];
  searchName = '';

  ngOnInit(): void {
    this.getAll();
  }

  constructor(private schoolService:SchoolService,private router:Router,private route:ActivatedRoute,private commonService:CommonService) {
  }

  getAll(): void {
    this.schoolService.getAll().subscribe(data => {
      this.schools = data;
    });
  }

  onEdit(schoolId: number): void {
    this.router.navigate(['edit', schoolId], { relativeTo: this.route });
  }

  onDelete(schoolsId: number): void {
    this.schoolService.deleteSchool(schoolsId).subscribe(response => {
      if (response.status) {
        this.commonService.showSuccessAlert(response.message);
        this.getAll();
      } else {
        this.commonService.showErrorAlert(response.message);
      }
    })
  }

  onSearch(): void {
    if (this.searchName) {
      this.schoolService.searchSchools(this.searchName).subscribe(data => {
        this.schools = data;
      });
    } else {
      this.getAll();
    }
  }
}

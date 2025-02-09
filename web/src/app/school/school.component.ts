import {Component, OnInit} from '@angular/core';
import {School} from "../entity/school";
import {SchoolService} from "../service/school.service";
import {ActivatedRoute, Router} from "@angular/router";

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

  constructor(private schoolService:SchoolService,private router:Router,private route:ActivatedRoute) {
  }

  getAll(): void {
    this.schoolService.getAll().subscribe(data => {
      this.schools = data;
    });
  }

  onEdit(schoolId: number): void {
    console.log('Navigating to edit with id:', schoolId);
    this.router.navigate(['edit', schoolId], { relativeTo: this.route });
  }

  onDelete(schoolsId: number): void {}

  onSearch(): void {}
}

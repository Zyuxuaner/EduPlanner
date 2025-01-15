import {Component, OnInit} from '@angular/core';
import {Student} from "../entity/student";
import {StudentService} from "../service/student.service";

@Component({
  selector: 'app-student',
  templateUrl: './student.component.html',
  styleUrls: ['./student.component.css']
})
export class StudentComponent implements OnInit {
  searchName = '';
  searchStudentSno = '';
  students: Student[] = [];

  constructor(private studentService: StudentService) {
  }

  ngOnInit(): void {
    this.getAll();
  }

  getAll(): void {
    this.studentService.getAll().subscribe(data => {
      this.students = data;
    });
  }
  onEdit(id: number): void {}

  onActive(id: number): void {}

  onSearch() {}

  onDelete(id: number): void {}
}

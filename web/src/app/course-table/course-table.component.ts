import {Component, OnInit} from '@angular/core';
import {LoginService} from "../service/login.service";
import {Person} from "../entity/person";

@Component({
  selector: 'app-course-table',
  templateUrl: './course-table.component.html',
  styleUrls: ['./course-table.component.css']
})
export class CourseTableComponent implements OnInit{
  person = {} as Person;

  constructor(private loginService: LoginService) { }
    ngOnInit(): void {
      this.loginService.currentLoginUser().subscribe(userData => {
        if (userData.status) {
          this.person.role = userData.data.role;
        }
      })
    }

}

import {Component, OnInit} from '@angular/core';
import {LoginService} from "../service/login.service";
import {Person} from "../entity/person";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  person = {} as Person;

  constructor(private loginService: LoginService) {}

  ngOnInit(): void {
    this.loginService.currentLoginUser().subscribe(userData => {
      if (userData.status) {
        this.person.role = userData.data.role;
      }
    });
  }

}

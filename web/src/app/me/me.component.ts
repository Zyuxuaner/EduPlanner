import {Component, OnInit} from '@angular/core';
import {LoginService} from "../service/login.service";
import {CommonService} from "../service/common.service";
import {Person} from "../entity/person";
import {PersonService} from "../service/person.service";
import {HttpParams} from "@angular/common/http";

@Component({
  selector: 'app-me',
  templateUrl: './me.component.html',
  styleUrls: ['./me.component.css']
})
export class MeComponent implements OnInit{
  person = {} as Person;
  oldPassword = '';
  newPassword = '';
  showOldPassword = false;
  showNewPassword = false;

  constructor(private loginService: LoginService,
              private personService: PersonService,
              private commonService: CommonService) { }

  ngOnInit(): void {
    this.loginService.currentLoginUser().subscribe(userResponse => {
      console.log(userResponse,1);
      if (userResponse.status) {
          this.person.name = userResponse.data.name;
          this.person.username = userResponse.data.username;
          this.person.role = userResponse.data.role;
          this.person.no = userResponse.data.no;
      }
    });
  }

  togglePasswordVisibility(): void {
    this.showNewPassword = !this.showNewPassword;
  }

  changePassword(): void {
    const httpParams = new HttpParams()
      .append('oldPassword', this.oldPassword)
      .append('newPassword', this.newPassword);

    this.personService.changePassword(httpParams).subscribe(
      responseBody => {
        if (responseBody.status) {
          this.commonService.showSuccessAlert(responseBody.message);
        } else {
          this.commonService.showErrorAlert(responseBody.message);
        }
      }, error => this.commonService.showErrorAlert('请求失败。请稍后')
    );
  }


}

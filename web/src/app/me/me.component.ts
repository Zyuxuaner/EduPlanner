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
  userNo = '';

  constructor(private loginService: LoginService,
              private personService: PersonService,
              private commonService: CommonService) { }

  ngOnInit(): void {
    this.loginService.currentLoginUser().subscribe((response) => {
      // @ts-ignore
      if (response.status) {
        // @ts-ignore
        const user = response.data;
        (this.person as any).name = user.username;
        (this.person as any).username = user.username;
        (this.person as any).role = user.role;
        // 根据角色判断显示学号还是工号
        if (user.role === 1) {
          // 学生
          const student = (user as any).student;
          if (student) {
            this.userNo = student.sno;
          }
        } else if (user.role === 2 || user.role === 3) {
          // 管理员
          const admin = (user as any).admin;
          if (admin) {
            this.userNo = admin.ano;
          }
        }
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

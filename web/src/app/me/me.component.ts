import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {LoginService} from "../service/login.service";
import {CommonService} from "../service/common.service";
import {Person} from "../entity/person";
import {PersonService} from "../service/person.service";
import {HttpParams} from "@angular/common/http";
import {Router} from "@angular/router";

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

  @ViewChild('exampleModal') modalElement: ElementRef | undefined;

  constructor(private loginService: LoginService,
              private personService: PersonService,
              private commonService: CommonService,
              private router: Router) { }

  ngOnInit(): void {
    this.loginService.currentLoginUser().subscribe(userResponse => {
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
    // 验证新密码长度是否为 6 位
    if (this.newPassword.length!== 6) {
      this.commonService.showErrorAlert('新密码必须为 6 位，请重新输入');
      return;
    }

    // 验证新密码是否等于旧密码
    if (this.newPassword === this.oldPassword) {
      this.commonService.showErrorAlert('新密码不能与旧密码相同');
      return;
    }

    const httpParams = new HttpParams()
      .append('oldPassword', this.oldPassword)
      .append('newPassword', this.newPassword);

    this.personService.changePassword(httpParams).subscribe({
      next: (responseBody) => {
        if (responseBody.status) {
          this.commonService.showSuccessAlert(responseBody.message);
          this.commonService.showLoadingAlert('更换密码后，请您重新登录');
          // 模拟异步任务，2 秒后关闭加载提示并进行页面跳转
          setTimeout(() => {
            this.commonService.closeLoadingAlert();
            this.closeModal();
            this.router.navigate(['/login']);
          }, 2000);
        } else {
          if (responseBody.message === '旧密码错误') {
            this.commonService.showErrorAlert('旧密码不正确，请重新输入');
          } else {
            this.commonService.showErrorAlert(responseBody.message);
          }
        }
      }
    });
  }

  closeModal(): void {
    // @ts-ignore
    this.modalElement.nativeElement.click();
  }
}

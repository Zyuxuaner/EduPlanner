import { Component } from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {LoginService} from "../service/login.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent{
  formGroup= new FormGroup({
    username: new FormControl('', Validators.required),
    password: new FormControl('', [Validators.required,Validators.pattern(/^\d{6}$/)])
  });
  showPassword = false;

  constructor(private loginService: LoginService,
              private router: Router) {
  }

  togglePasswordVisibility(): void {
    this.showPassword = !this.showPassword;
  }

  onLogin(): void {
    const formValue = this.formGroup.value;
    const user = {
      username: formValue.username!,
      password: formValue.password!,
    };
    this.loginService.login(user).subscribe(response => {
      if (response.status) {
        this.router.navigate(['/dashboard']);
      } else {
        console.log('登录失败');
      }
    });
  }
}

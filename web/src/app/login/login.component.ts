import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {LoginService} from "../service/login.service";
import {ActivatedRoute, Router} from "@angular/router";
import {CommonService} from "../service/common.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  formGroup= new FormGroup({
    username: new FormControl('', Validators.required),
    password: new FormControl('', Validators.required)
  });
  showPassword = false;
  redirectUrl = '/dashboard';

  constructor(private loginService: LoginService,
              private router: Router,
              private activatedRoute: ActivatedRoute,
              private commonService: CommonService) {
  }

  ngOnInit(): void {
    this.activatedRoute.params.subscribe(params => {
      this.redirectUrl = params['redirectUrl'] || '/dashboard';
    })
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
        this.loginService.setIsLogin(true);
        this.router.navigate([this.redirectUrl]);
      } else {
        this.commonService.showErrorAlert(response.message)
      }
    }, error => console.log(error));
  }
}

import { Component } from '@angular/core';
import {LoginService} from "../../service/login.service";
import {Router} from "@angular/router";
import {User} from "../../entity/user";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent {
  constructor(private loginService: LoginService,
              private router: Router) {
  }
  onLogout(): void {
    this.loginService.logout().subscribe( response => {
      if (response.status) {
        this.loginService.setIsLogin(false);
        this.router.navigate(['/login']);
      }
    })
  }
}

import {Component, OnInit} from '@angular/core';
import {LoginService} from "../../service/login.service";
import {Router} from "@angular/router";
import {User} from "../../entity/user";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit{
  user = {} as User;
  constructor(private loginService: LoginService,
              private router: Router) {
  }

  ngOnInit(): void {
        this.loginService.currentLoginUser().subscribe(userData  => {
          this.user = userData.data;
        })
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

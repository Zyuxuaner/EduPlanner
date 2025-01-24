import {Component, OnInit} from '@angular/core';
import {LoginService} from "../../service/login.service";

@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.css']
})
export class NavComponent implements OnInit {
  filteredMenus: Array<any> | undefined;
  menus: Array<any> = [
    {link: 'dashboard', icon: 'fa-solid fa-house', name: '首页', exact: true, roles: [1, 2, 3]},
    {link: 'school', icon: 'fas fa-university', name: '学校管理', exact: false,roles: [2, 3]},
    {link: 'term', icon: 'fa-solid fa-calendar-days', name: '学期管理', exact: false, roles: [1, 2, 3]},
    {link: 'clazz', icon: 'fa fa-graduation-cap', name: '班级管理', exact: false, roles: [2, 3]},
    {link: 'admin', icon: 'fa fa-users', name: '管理员列表', exact: false, roles: [2, 3]},
    {link: 'student', icon: 'fa-solid fa-address-book', name: '学生列表', exact: false, roles: [2, 3]},
    {link: 'course', icon: 'fa fa-book', name: '课程管理', exact: false, roles: [1]},
    {link: 'courseTable', icon: 'fa-solid fa-layer-group', name: '课表查询', exact: false, roles: [1, 2, 3]},
    {link: 'me', icon: 'fa-solid fa-circle-user', name: '个人中心', exact: false, roles: [1, 2, 3]}
  ];

  constructor(private loginService: LoginService) {
  }

  ngOnInit() {
    this.filterMenus();
  }

  filterMenus(): void {
    this.loginService.currentLoginUser().subscribe(response => {
      const userRole = response.data.role;
      this.filteredMenus = this.menus.filter(menu => menu.roles.includes(userRole));
    });
  }
}

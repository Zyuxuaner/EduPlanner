import { Component } from '@angular/core';

@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.css']
})
export class NavComponent {

  menus: Array<any> = [
    {link: 'dashboard', icon: 'fa-solid fa-house', name: '首页', exact: true,},
    {link: 'school', icon: 'fas fa-university', name: '学校管理', exact: false,},
    {link: 'term', icon: 'fa-solid fa-calendar-days', name: '学期管理', exact: false,},
    {link: 'clazz', icon: 'fa fa-graduation-cap', name: '班级管理', exact: false,},
    {link: 'admin', icon: 'fa fa-users', name: '管理员列表', exact: false,},
    {link: 'student', icon: 'fa-solid fa-address-book', name: '学生列表', exact: false,},
    {link: 'course', icon: 'fa fa-book', name: '课程管理', exact: false,},
    {link: 'course-table', icon: 'fa-solid fa-layer-group', name: '课表查询', exact: false,},
    {link: 'me', icon: 'fa-solid fa-circle-user', name: '个人中心', exact: false,}
  ];
}

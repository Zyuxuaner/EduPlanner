import { Component } from '@angular/core';
import {Admin} from "../entity/admin";

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent {
  private roleMap: Map<number, string> = new Map([
    [1, '超级管理员'],
    [2, '管理员']
  ]);

  admins: Admin[] = [
    { id: 1, name: '梦云智', ano: 123456, user: { id: 1, username: 'mengyunzhi', password: '12345', role: 1} },
    { id: 2, name: '李四', ano: 873624, user: { id: 2, username: 'lisi', password: '873624', role: 2} },
    { id: 3, name: '王五', ano: 983645, user: { id: 3, username: 'wangwu', password: '983645', role: 2} },
    { id: 4, name: '赵六', ano: 883762, user: { id: 4, username: 'zhaoliu', password: '883762', role: 2} },
  ];

  constructor() {
  }

  getRoleName(role: number): string {
    return this.roleMap.get(role) || '未知角色';
  }
}

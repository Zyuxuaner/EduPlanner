import {Component, OnInit} from '@angular/core';
import {Admin} from "../entity/admin";
import {AdminService} from "../service/admin.service";

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {
  private roleMap: Map<number, string> = new Map([
    [1, '超级管理员'],
    [2, '管理员']
  ]);

  admins: Admin[] = [];

  constructor(private adminService: AdminService) {
  }

  ngOnInit(): void {
    this.getAllAdmins();
    }

  getRoleName(role: number): string {
    return this.roleMap.get(role) || '未知角色';
  }

  getAllAdmins(): void {
    this.adminService.getAll().subscribe(data => {
      this.admins = data;
    });
  }
}

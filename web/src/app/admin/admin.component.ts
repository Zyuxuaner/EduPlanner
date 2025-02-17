import {Component, OnInit} from '@angular/core';
import {Admin} from "../entity/admin";
import {AdminService} from "../service/admin.service";
import {CommonService} from "../service/common.service";
import {LoginService} from "../service/login.service";

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {
  private roleMap: Map<number, string> = new Map([
    [3, '超级管理员'],
    [2, '管理员']
  ]);
  admins: Admin[] = [];
  currentAdminId: number | undefined;
  currentAdminRole: number | null = null;

  constructor(private adminService: AdminService,
              private commonService: CommonService,
              private loginService: LoginService) {
  }

  ngOnInit(): void {
    this.loginService.currentLoginUser().subscribe(response => {
      if (response.status) {
        this.currentAdminId = response.data.id;
        this.currentAdminRole = response.data.role;
      }
      console.log(response);
      console.log(this.currentAdminId);
    })
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

  onSearch(): void {

  }

  onDelete(id: number): void {
    this.adminService.delete(id).subscribe(response => {
      if (response.status) {
        this.commonService.showSuccessAlert(response.message);
        this.getAllAdmins();
      } else {
        this.commonService.showErrorAlert(response.message);
      }
    })
  }

  onEdit(id: number): void {

  }

  resetPassword(id: number): void {
    const constPassword = "123456"
    this.adminService.resetPassword(id, constPassword).subscribe(response => {
      this.commonService.showSuccessAlert(response.message);
    })
  }
}

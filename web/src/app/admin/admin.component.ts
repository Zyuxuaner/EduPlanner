import {Component, OnInit} from '@angular/core';
import {Admin} from "../entity/admin";
import {AdminService} from "../service/admin.service";
import {CommonService} from "../service/common.service";

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

  constructor(private adminService: AdminService,
              private commonService: CommonService) {
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

  onDelete(id: number): void {
    this.commonService.showConfirmAlert(() => {
      this.adminService.delete(id).subscribe(response => {
        if (response.status) {
          this.commonService.showSuccessAlert(response.message);
          this.getAllAdmins();
        } else {
          this.commonService.showErrorAlert(response.message);
        }
      }, error => console.log(error));
    }, '是否删除，此操作不可逆');
  }
}

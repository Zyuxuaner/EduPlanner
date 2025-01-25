import {Component, OnInit} from '@angular/core';
import {Term} from '../entity/term';
import {TermService} from "../service/term.service";
import {CommonService} from "../service/common.service";

@Component({
  selector: 'app-term',
  templateUrl: './term.component.html',
  styleUrls: ['./term.component.css']
})
export class TermComponent implements OnInit {
  terms: Term[] = [];

  constructor(private termService: TermService,private commonService: CommonService) {
  }

  ngOnInit() {
    this.getAll();
  }

  getAll(): void {
    this.termService.getAll().subscribe(data => {
      this.terms = data;
    });
  }

  onDelete(id: number): void {
  }

  onEdit(id: number): void {
  }

  onActive(id: number): void {
    this.updateStatus(id, 1);
  }

  onFreeze(id: number): void {
    this.updateStatus(id, 0);
  }

  updateStatus(id: number, status: number): void {
    this.termService.updateStatus(id, status).subscribe(() => {
      this.getAll();
      if (status === 1) {
        this.commonService.showSuccessAlert('该学期已激活，其他学期已自动冻结。');
      } else {
        this.commonService.showSuccessAlert('该学期已冻结。');
      }
    }, error => {
      this.commonService.showErrorAlert('更新状态失败，请稍后重试。');
    });
  }
}

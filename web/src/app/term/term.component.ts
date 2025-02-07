import {Component, OnInit} from '@angular/core';
import {Term} from '../entity/term';
import {TermService} from "../service/term.service";
import {Person} from "../entity/person";
import {LoginService} from "../service/login.service";
import {CommonService} from "../service/common.service";

@Component({
  selector: 'app-term',
  templateUrl: './term.component.html',
  styleUrls: ['./term.component.css']
})
export class TermComponent implements OnInit {
  terms: Term[] = [];
  person = {} as Person;

  constructor(private termService: TermService,
              private loginService: LoginService,
              private commonService: CommonService) {
  }

  ngOnInit() {
    this.loginService.currentLoginUser().subscribe(userData => {
      if (userData.status) {
        this.person.role = userData.data.role;
      }})
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
    this.commonService.showConfirmAlert(() => {
      this.termService.active(id).subscribe((responseBody) => {
        if (responseBody.status) {
          this.commonService.showSuccessAlert(responseBody.message);
          this.getAll();
        } else {
          this.commonService.showErrorAlert(responseBody.message);
        }
      });
    }, '是否激活，此操作不可逆');
  }
}

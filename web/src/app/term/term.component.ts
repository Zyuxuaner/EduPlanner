import {Component, OnInit} from '@angular/core';
import {Term} from '../entity/term';
import {TermService} from "../service/term.service";
import {Person} from "../entity/person";
import {LoginService} from "../service/login.service";
import {CommonService} from "../service/common.service";
import {ActivatedRoute, Router} from "@angular/router";
import {FormControl, FormGroup} from "@angular/forms";
import {SchoolImpl} from "../entity/school";

@Component({
  selector: 'app-term',
  templateUrl: './term.component.html',
  styleUrls: ['./term.component.css']
})
export class TermComponent implements OnInit {
  selectedSchool: SchoolImpl | null = null;
  terms: Term[] = [];
  person = {} as Person;
  formGroup: FormGroup = new FormGroup({
    schoolId: new FormControl(),
    searchName: new FormControl(),
  })

  constructor(private termService: TermService,
              private loginService: LoginService,
              private commonService: CommonService,
              private router: Router,
              private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.loginService.currentLoginUser().subscribe(userData => {
      if (userData.status) {
        this.person.role = userData.data.role;
      }})
    this.getAll();
  }

  // 当学校选择变化时，更新 school
  onSchoolChange(school: SchoolImpl | null): void {
    this.selectedSchool = school;
  }

  onSearch(): void {
    const { schoolId, searchName } = this.formGroup.value;
    this.termService.search(schoolId, searchName).subscribe(data => {
      this.terms = data;
    });
  }

  getAll(): void {
    this.termService.getAll().subscribe(data => {
      this.terms = data;
    });
  }

  onDelete(id: number): void {
    this.termService.deleteTerm(id).subscribe((response) => {
      if (response.status) {
        this.commonService.showSuccessAlert(response.message);
        this.getAll();
      } else {
        this.commonService.showErrorAlert(response.message);
      }
    })
  }

  onEdit(id: number) {
    this.router.navigate(['edit', id], { relativeTo: this.route });
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

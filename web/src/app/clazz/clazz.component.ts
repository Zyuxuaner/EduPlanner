import {Component, OnInit} from '@angular/core';
import {Clazz} from "../entity/clazz";
import {ClazzService} from "../service/clazz.service";
import {FormControl, FormGroup} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {CommonService} from "../service/common.service";

@Component({
  selector: 'app-clazz',
  templateUrl: './clazz.component.html',
  styleUrls: ['./clazz.component.css']
})
export class ClazzComponent implements OnInit {
  clazzes: Clazz[] = [];
  searchForm!: FormGroup;

  constructor(private clazzService: ClazzService,private router: Router, private route: ActivatedRoute,private commonService: CommonService) {
  }

  ngOnInit(): void {
    this.searchForm = new FormGroup({
      school_id: new FormControl(),
      clazzName: new FormControl()
    });
    this.getAll();
  }

  getAll(): void {
    this.clazzService.getAll().subscribe(data => {
      this.clazzes = data;
    })
  }

  onEdit(clazzId: number) {
    this.router.navigate(['edit', clazzId], { relativeTo: this.route });

  }

  onDelete(clazzId: number) {
    this.clazzService.deleteClazz(clazzId).subscribe(
      response => {
        if (response.status) {
          this.commonService.showSuccessAlert(response.message);
          this.getAll();
        } else {
          this.commonService.showErrorAlert(response.message);
        }
      }
    )
  }

  onSearch(): void {
    const { school_id: schoolId, clazzName } = this.searchForm.value;
    this.clazzService.searchClazzes(schoolId, clazzName).subscribe(data => {
      this.clazzes = data;
    });
  }
}

import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {AdminService} from "../../service/admin.service";
import {ActivatedRoute, Router} from "@angular/router";
import {CommonService} from "../../service/common.service";

@Component({
  selector: 'app-edit',
  templateUrl: './edit.component.html',
  styleUrls: ['./edit.component.css']
})
export class EditComponent implements OnInit {
  formGroup = new FormGroup({
    name: new FormControl('', Validators.required),
    username: new FormControl('', Validators.required),
    ano: new FormControl('', [Validators.required,Validators.pattern(/^\d{6}$/)]),
  });
  id = Number(this.route.snapshot.paramMap.get('id'));

  constructor(private adminService: AdminService, private router: Router,private commonService: CommonService,private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.adminService.getAdminById(this.id).subscribe(admin => {
      if (this.formGroup) {
        this.formGroup.patchValue({
          name: admin.name,
          username: admin.user.username,
          ano: admin.ano,
        })
      }
    })
  }

  onSubmit(): void {
    const adminData = { ...this.formGroup.value, id: this.id };
    this.adminService.updateAdmin(adminData).subscribe(response => {
      if (response.status) {
        this.commonService.showSuccessAlert(response.message);
        this.router.navigate(['/admin']);
      } else {
        this.commonService.showErrorAlert(response.message);
      }
    });
  }

}

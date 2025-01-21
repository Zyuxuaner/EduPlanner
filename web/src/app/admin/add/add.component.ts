import {Component, OnInit} from '@angular/core';
import {AdminService} from "../../service/admin.service";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-add',
  templateUrl: './add.component.html',
  styleUrls: ['./add.component.css']
})
export class AddComponent implements OnInit {
  formGroup = new FormGroup({
    name: new FormControl('', Validators.required),
    username: new FormControl('', Validators.required),
    ano: new FormControl('', [Validators.required,Validators.pattern(/^\d{6}$/)]),
    role: new FormControl(2, Validators.required)
  });

  constructor(private adminService: AdminService,
              private router: Router) {
  }

  ngOnInit(): void {
  }

  onSubmit() {
    const admin = this.formGroup.value as { name: string; username: string; ano: string; role: number };
    this.adminService.add(admin).subscribe(value => {
      console.log(value);
      this.router.navigate(['/admin']);
    });
  }
}

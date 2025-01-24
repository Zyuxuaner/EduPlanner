import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {ClazzService} from "../../service/clazz.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-add',
  templateUrl: './add.component.html',
  styleUrls: ['./add.component.css']
})
export class AddComponent implements OnInit {
  formGroup = new FormGroup({
    clazz: new FormControl('', Validators.required),
    school_id: new FormControl(null, Validators.required)
  });

  constructor(private clazzService: ClazzService,
              private router: Router) { }

  ngOnInit(): void {
  }

  onSubmit(): void {
    const formValue = this.formGroup.value;
    const schoolId = formValue.school_id!;
    const clazz = {
      name: formValue.clazz!,
      school: {id: schoolId, name: 'undefined'}
    } as {name: string; school: {id: number, name: string}
    };
    this.clazzService.add(clazz).subscribe(data => {
          this.router.navigate(['/clazz']);
        });
  }
}

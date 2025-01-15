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
    const addClazz = this.formGroup.value as { name: string, schoolId: number};
    this.clazzService.add(addClazz).subscribe(data => {
      console.log(data);
      alert('添加成功');
      this.router.navigate(['/clazz']);
    });
  }
}

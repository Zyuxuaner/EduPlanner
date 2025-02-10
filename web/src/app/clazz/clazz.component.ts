import {Component, OnInit} from '@angular/core';
import {Clazz} from "../entity/clazz";
import {ClazzService} from "../service/clazz.service";
import {FormControl, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-clazz',
  templateUrl: './clazz.component.html',
  styleUrls: ['./clazz.component.css']
})
export class ClazzComponent implements OnInit {
  clazzes: Clazz[] = [];
  searchForm!: FormGroup;

  constructor(private clazzService: ClazzService) {
  }

  ngOnInit(): void {
    this.searchForm = new FormGroup({
      school_id: new FormControl()
    });
    this.clazzService.getAll().subscribe(data => {
        this.clazzes = data;
    })
  }
}

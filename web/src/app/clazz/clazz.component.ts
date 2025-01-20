import {Component, OnInit} from '@angular/core';
import {Clazz} from "../entity/clazz";
import {ClazzService} from "../service/clazz.service";

@Component({
  selector: 'app-clazz',
  templateUrl: './clazz.component.html',
  styleUrls: ['./clazz.component.css']
})
export class ClazzComponent implements OnInit {
  clazzes: Clazz[] = [];
    constructor(private clazzService: ClazzService) {
    }
    ngOnInit(): void {
    this.clazzService.getAll().subscribe(
      data => {
        this.clazzes = data;
        }
      )
    }
}

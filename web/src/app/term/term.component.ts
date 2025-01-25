import {Component, OnInit} from '@angular/core';
import {Term} from '../entity/term';
import {TermService} from "../service/term.service";

@Component({
  selector: 'app-term',
  templateUrl: './term.component.html',
  styleUrls: ['./term.component.css']
})
export class TermComponent implements OnInit {
  terms: Term[] = [];

  constructor(private termService: TermService) {
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
    });
  }
}

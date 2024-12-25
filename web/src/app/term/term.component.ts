import { Component } from '@angular/core';
import {Term} from '../entity/term';

@Component({
  selector: 'app-term',
  templateUrl: './term.component.html',
  styleUrls: ['./term.component.css']
})
export class TermComponent {
  terms: Term[] = [
    {id: 1, name: '2024年秋', startTime: 1725120000000, endTime: 1735488000000, status: 0, school: {id: 1, name: '天职师大'}},
    {id: 2, name: '2024年秋', startTime: 1725120000000, endTime: 1733760000000, status: 0, school: {id: 1, name: '河工大'}},
    {id: 3, name: '2024年春', startTime: 1709222400000, endTime: 1717171200000, status: 1, school: {id: 1, name: '天职师大'}}
  ];

  onDelete(id: number): void {
  }

  onEdit(id: number): void {
  }

  onActive(id: number): void {
  }
}

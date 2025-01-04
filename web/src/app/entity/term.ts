import {School} from './school';

export interface Term {
  id: number;
  name: string;
  startTime: number;
  endTime: number;
  status: number;
  school: School;
}

import {Term} from "./term";

export interface Course {
  id:number;
  name: string;
  term: Term;
  type: number;
  status: string
  weeks: number[];
  start_time: number;
  end_time: number;
  week: number;
  range: string;
  begin: number;
  end: number;
  courseInfoId: number;
}

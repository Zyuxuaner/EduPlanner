import {Term} from "./term";

export interface Course {
  id:number;
  name: string;
  term: Term;
  // 选/必
  type: number;
  // 单双周
  weekType: number
  startWeek: number;
  endWeek: number;
  week: number;
  begin: number;
  length: number;
  courseInfoId: number;
}

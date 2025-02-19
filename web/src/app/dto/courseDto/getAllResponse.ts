import {Term} from "../../entity/term";
import {Student} from "../../entity/student";

export interface GetAllResponse {
  name: string;
  term: Term;
  creator: Student;
  courseInfo: CourseInfoEntity;
  reuseStudents: Student[];
}

// 和后台实体相同的 courseInfo 实体
export type CourseInfoEntity = {
  id: number;
  weekType: string;
  weeks: number[];
  day: number;
  begin: number;
  length: number;
}

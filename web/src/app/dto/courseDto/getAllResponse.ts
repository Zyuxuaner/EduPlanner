import {Term} from "../../entity/term";
import {Student} from "../../entity/student";

export interface GetAllResponse {
  name: string;
  weekType: string;
  weeks: number[];
  day: number;
  begin: number;
  length: number;
  term: Term;
  student: Student;
}

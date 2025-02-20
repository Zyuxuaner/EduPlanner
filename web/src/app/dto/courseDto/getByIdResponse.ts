import {Term} from "../../entity/term";
import {CourseInfoEntity} from "./getAllResponse";

export interface GetByIdResponse {
  name: string;
  term: Term;
  totalWeeks: number;
  courseInfo: CourseInfoEntity;
}

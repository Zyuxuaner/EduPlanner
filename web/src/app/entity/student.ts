import {User} from "./user";
import {School} from "./school";

export interface Student {
  id: number;
  name: string;
  sno: string;
  status: number;
  school: School;
  user: User;
}

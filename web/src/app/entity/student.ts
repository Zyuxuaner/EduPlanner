import {Clazz} from "./clazz";
import {User} from "./user";

export interface Student {
  id: number;
  name: string;
  sno: string;
  status: number;
  clazz: Clazz;
  user: User;
}

import {School} from "./school";

export interface Clazz {
  id: number;
  school: School;
  name: string;
}

export class ClazzImpl implements Clazz {
  constructor(public id: number, public school: School, public name: string) {
  }
}

export interface School {
  id: number;
  name: string;
}

export class SchoolImpl implements School {
  constructor(public id: number, public name: string) {}
}

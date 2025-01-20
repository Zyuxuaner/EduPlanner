export interface ResponseBody {
  status: boolean;
  message: string;
  data?: any;
}

export class ResponseBodyImpl implements ResponseBody {
  constructor(public status: boolean, public message: string, public data: any) {
  }
}

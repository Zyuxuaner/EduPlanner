/**
 * 分页
 * 因为content数组中的元素类型是不确定的，用一个泛型表示content中数组元素的类型
 * const page = new Page<Clazz>()时将content的类型设置为Clazz[]
 */
export class Page<T> {
  content: T[];
  first: boolean;
  last: boolean;
  // 当前为第几页（由1开始）
  number: number;
  // 每页多少条
  size: number;
  // 数据总条数
  numberOfElements: number;
  // 总页数
  totalPages: number;
  constructor(data: {
    content: T[], // 必然要设置的属性，否则Page无法工作
    first?: boolean, // 加?表示为可选属性
    last?: boolean,
    number: number,
    size: number,
    numberOfElements: number,
    totalPages: number}) {
    this.content = data.content;
    this.number = data.number;
    this.size = data.size;
    this.numberOfElements = data.numberOfElements;
    this.totalPages = data.totalPages;
    if (data.first !== undefined) {
      this.first = data.first;
    } else {
      this.first = this.number === 0;
    }
    if (data.last !== undefined) {
      this.last = data.last;
    } else {
      this.last = (this.number + 1) * this.size >= this.numberOfElements;
    }
  }
}

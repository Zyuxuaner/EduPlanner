// 引入相关的实体接口
import { User } from "./user";
import { Student } from "./student";
import { Admin } from "./admin";

// 定义 Person 接口
export interface Person {
  user: User;
  // 判断当前 Person 是学生还是管理员的方法
  isStudent(): boolean;
  isAdmin(): boolean;
}

// 实现 StudentPerson 类，用于表示学生类型的 Person
export class StudentPerson implements Person {
  constructor(public user: User, public student: Student) {}

  // 判断是否为学生，返回 true
  isStudent(): boolean {
    return this.user.role === 1;
  }

  // 判断是否为管理员，返回 false
  isAdmin(): boolean {
    return false;
  }
}

// 实现 AdminPerson 类，用于表示管理员类型的 Person
export class AdminPerson implements Person {
  constructor(public user: User, public admin: Admin) {}

  // 判断是否为学生，返回 false
  isStudent(): boolean {
    return false;
  }

  // 判断是否为管理员，返回 true
  isAdmin(): boolean {
    return this.user.role === 2 || this.user.role === 3;
  }
}

// 工厂函数，根据 User 的 role 字段创建对应的 Person 实例
export function createPerson(user: User, student?: Student, admin?: Admin): Person {
  if (user.role === 1 && student) {
    return new StudentPerson(user, student);
  } else if ((user.role === 2 || user.role === 3) && admin) {
    return new AdminPerson(user, admin);
  }
  throw new Error("非法role或缺少学生信息或管理员信息");
}

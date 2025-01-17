export interface CourseInfo {
  courseName ?: string;   // 课程名称
  startWeek ?: number;   // 开始周数
  endWeek ?: number;     // 结束周数
  status ?: string;     // 状态(单周、双周、全周)
  students ?: string[];
  begin ?: number;  // 第几小节开始上课
  length?: number;  // 总共的小节数
}

// 定义单日的课表结构
export type DaySchedule = {
  [time: number]: CourseInfo; // 课程小节数（1 表示第一小节）作为键，值为课程信息
};

// 定义整周课表的结构
export type WeeklySchedule = {
  [day: number]: DaySchedule; // 周几作为键（1 表示周一，2 表示周二等），值为对应的 DaySchedule
};

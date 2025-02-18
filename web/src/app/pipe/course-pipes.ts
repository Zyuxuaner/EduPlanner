import { Pipe, PipeTransform} from "@angular/core";

@Pipe({
  standalone: true,
  name: 'dayFormat'
})
export class DayFormatPipe implements PipeTransform {
  // 转换小节数
  transform(day: number): string {
    if (day === 0) {
      return '未知'
    }
    return `第${day}小节`;
  }
}

@Pipe({
  standalone: true,
  name: 'weekFormat'
})
export class WeekFormatPipe implements PipeTransform {
  // 转换星期数
  transform(week: number): string {
    if (week === 0) {
      return '未知';
    }
    const daysOfWeek = ['一', '二', '三', '四', '五', '六', '日'];
    return `星期${daysOfWeek[week - 1] || '未知'}`;
  }
}

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
  name: 'weekDayFormat'
})
export class WeekDayFormatPipe implements PipeTransform {
  // 转换星期数
  transform(week: number): string {
    if (week === 0) {
      return '未知';
    }
    const daysOfWeek = ['一', '二', '三', '四', '五', '六', '日'];
    return `星期${daysOfWeek[week - 1] || '未知'}`;
  }
}

@Pipe({
  standalone: true,
  name: 'weekRangeFormat'
})
export class WeekRangeFormatPipe implements PipeTransform {

  transform(weeks: number[], weekType: string): string {
    if (!weeks || weeks.length === 0 || !weekType) {
      return '';
    }

    // 排序 weeks 数组
    weeks.sort((a, b) => a - b);

    let result = '';

    switch (weekType) {
      case 'all':
        // 全周
        result = `${weeks[0]} - ${weeks[weeks.length - 1]} 周`;
        break;
      case 'odd':
        // 单周，过滤出所有奇数周
        const oddWeeks = weeks.filter(week => week % 2 !== 0);
        result = this.formatWeeks(oddWeeks, 'odd');
        break;
      case 'even':
        // 双周，过滤出所有偶数周
        const evenWeeks = weeks.filter(week => week % 2 === 0);
        result = this.formatWeeks(evenWeeks, 'even');
        break;
      case 'other':
        // 自定义周
        result = this.formatOtherWeeks(weeks);
        break;
      default:
        result = '';
    }

    return result;
  }

  private formatWeeks(weeks: number[], weekType: string): string {
    let formatted = '';
    let currentRangeStart = weeks[0];
    let currentRangeEnd = weeks[0];

    // 遍历 weeks 数组，构建周次范围
    for (let i = 1; i < weeks.length; i++) {
      // 判断当前周是否连续，并且考虑是单周还是双周
      const isConsecutive = weeks[i] === weeks[i - 1] + 2; // 这里判断是否为单周或双周连续
      const isOddWeek = weekType === 'odd' && weeks[i] % 2 !== 0;
      const isEvenWeek = weekType === 'even' && weeks[i] % 2 === 0;

      if (isConsecutive && ((weekType === 'odd' && isOddWeek) || (weekType === 'even' && isEvenWeek))) {
        // 连续的单周或双周
        currentRangeEnd = weeks[i];
      } else {
        // 非连续的周，添加范围并重置
        formatted += this.formatRange(currentRangeStart, currentRangeEnd);
        currentRangeStart = weeks[i];
        currentRangeEnd = weeks[i];
      }
    }

    // 最后一个范围
    formatted += this.formatRange(currentRangeStart, currentRangeEnd);

    return formatted;
  }

  private formatOtherWeeks(weeks: number[]): string {
    let formatted = '';
    let currentRangeStart = weeks[0];
    let currentRangeEnd = weeks[0];

    // 遍历 weeks 数组，构建周次范围
    for (let i = 1; i < weeks.length; i++) {
      if (weeks[i] === weeks[i - 1] + 1) {
        // 连续的周
        currentRangeEnd = weeks[i];
      } else {
        // 非连续的周，添加范围并重置
        formatted += this.formatRange(currentRangeStart, currentRangeEnd);
        currentRangeStart = weeks[i];
        currentRangeEnd = weeks[i];
      }
    }

    // 最后一个范围
    formatted += this.formatRange(currentRangeStart, currentRangeEnd);

    return formatted;
  }

  private formatRange(start: number, end: number): string {
    if (start === end) {
      return `${start} 周`;
    } else {
      return `${start} - ${end} 周`;
    }
  }
}

@Pipe({
  standalone: true,
  name: 'weekTypeFormat'
})
export class WeekTypeFormatPipe implements PipeTransform {
  transform(weekType: string): string {
    switch (weekType) {
      case 'all':
        return '(全周)';
      case 'odd':
        return '(单周)';
      case 'even':
        return '(双周)';
      case 'other':
        return '(自定义)';
      default:
        return '(未知)';
    }
  }
}

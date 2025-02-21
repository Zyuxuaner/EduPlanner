import {Component, EventEmitter, Input, OnInit, Output, SimpleChanges} from '@angular/core';
import {ControlValueAccessor, FormControl, NG_VALUE_ACCESSOR} from '@angular/forms';

@Component({
  selector: 'app-week-selector',
  templateUrl: './week-selector.component.html',
  styleUrls: ['./week-selector.component.css'],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: WeekSelectorComponent,
      multi: true
    }
  ]
})
export class WeekSelectorComponent implements ControlValueAccessor, OnInit {
  @Input() formControl: FormControl = new FormControl();
  @Input() weekType: 'other' | 'all' | 'odd' | 'even' = 'all'; // 接收父组件传递的weeks,默认全选
  @Output() weekSelectionChange = new EventEmitter<{ weeks: number[], weekType: 'other' | 'all' | 'odd' | 'even' }>(); // 向父组件传递选择结果

  selectedWeeks: number[] = []; // 存储已选择的周数
  allWeekList: number[] = [];
  private isFirstRender = true; // 标志位，用于判断是否是首次渲染

  @Input()
  set allWeeks(allWeeks: number) {
    if (allWeeks !== 0) {
      this.allWeekList = Array.from({length: allWeeks!}, (_, index) => index + 1);
      this.selectAllWeeks();
      if (this.isFirstRender) {
        // 初次渲染时直接根据传入的weeks和weekType渲染
        this.updateSelection();
      }
    }
  }

  @Input() weeks: number[] = [];

  ngOnInit(): void {
    this.updateSelection(); // 在组件初始化时更新选择
  }

  // 监听父组件传入的 weeks 和 weekType
  ngOnChanges(changes: SimpleChanges): void {
    if (changes['weeks']) {
      this.selectedWeeks = [...changes['weeks'].currentValue]; // 更新已选周数
    }

    if (changes['weekType']) {
      this.weekType = changes['weekType'].currentValue; // 更新周类型
    }

    if (this.isFirstRender) {
      this.updateSelection(); // 初次渲染时，根据传入值进行选择
      this.isFirstRender = false;
    }
  }


  // 根据周类型自动选中
  selectAllWeeks() {
    if (this.weekType === 'all') {
      this.selectedWeeks = [...this.allWeekList]; // 直接复制 allWeekList
    } else if (this.weekType === 'odd') {
      this.selectedWeeks = this.allWeekList.filter(week => week % 2 !== 0); // 筛选出奇数周
    } else if (this.weekType === 'even') {
      this.selectedWeeks = this.allWeekList.filter(week => week % 2 === 0); // 筛选出偶数周
    }
    this.updateWeekType();
    this.emitWeekSelection();
  }

  // 选择某一周
  toggleWeek(week: number) {
    const index = this.selectedWeeks.indexOf(week);
    if (index > -1) {
      // 如果周数已被选中，取消选中
      this.selectedWeeks.splice(index, 1);
    } else {
      // 如果周数未被选中，添加到已选中列表
      this.selectedWeeks.push(week);
    }
    this.updateWeekType();
    this.emitWeekSelection();
  }

  // 更新周类型（根据已选中的周数自动调整）
  updateWeekType() {
    const oddWeeksSelected = this.selectedWeeks.filter(week => week % 2 === 1).length;
    const evenWeeksSelected = this.selectedWeeks.filter(week => week % 2 === 0).length;

    if (this.selectedWeeks.length === this.allWeekList.length) {
      // 如果没有选中任何周
      this.weekType = 'all';
    } else if (oddWeeksSelected === this.selectedWeeks.length) {
      // 如果所有选中的周次都是单周
      this.weekType = 'odd';
    } else if (evenWeeksSelected === this.selectedWeeks.length) {
      // 如果所有选中的周次都是双周
      this.weekType = 'even';
    } else {
      // 如果选中的周数包含单周和双周
      this.weekType = 'other';  // 不选中任何周类型
    }
  }
  // 向父组件传递已选周数
  emitWeekSelection() {
    this.weekSelectionChange.emit({
      weeks: this.selectedWeeks,
      weekType: this.weekType
    });
    this.formControl.setValue(this.selectedWeeks);
  }

  writeValue(obj: any): void {
    if (obj) {
      this.selectedWeeks = [...obj]; // 如果父组件传递了值，更新 selectedWeeks
    }
  }
  registerOnChange(fn: any): void {
    this.formControl.valueChanges.subscribe(fn);
  }
  registerOnTouched(fn: any): void {
  }

  private updateSelection() {
    if (this.weeks.length > 0) {
      // 如果有父组件传递的 weeks，则直接更新
      this.selectedWeeks = [...this.weeks];
      this.selectAllWeeks(); // 根据传入的 weeks 和 weekType 更新选择
    }
  }

}

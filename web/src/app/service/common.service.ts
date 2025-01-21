import { Injectable } from '@angular/core';
import Swal, {SweetAlertResult} from 'sweetalert2';

@Injectable({
  providedIn: 'root'
})
export class CommonService {

  constructor() { }

  showSuccessAlert(message: string): void {
    Swal.fire({
      icon: 'success',
      title: '成功',
      text: message,
      showConfirmButton: false,
      timer: 1500
    });
  }

  showErrorAlert(message: string): void {
    Swal.fire({
      icon: 'error',
      title: '失败',
      text: message
    });
  }

  showConfirmAlert(callback?: () => void, description: string = '', title: string = '请确认'): void {
    Swal.fire({
      titleText: title,
      text: description,
      icon: 'question',
      confirmButtonText: '是',
      cancelButtonText: '否',
      showCancelButton: true,
      confirmButtonColor: '#007BFF',
      showCloseButton: true
    }).then((result: SweetAlertResult) => {
      if (result.value) {
        // 执行回调
        if (callback) {
          callback();
        }
      }
    });
  }
}

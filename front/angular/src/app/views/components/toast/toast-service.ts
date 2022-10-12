import { Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';

/**
 * トーストの表示を制御するサービス
 *
 * @export
 * @class ToastService
 */
@Injectable({
  providedIn: 'root',
})
export class ToastService {
  private actionMessage = 'Close';
  private duration = 5000;

  constructor(private snackBar: MatSnackBar) {}

  /**
   * トーストを表示する
   *
   * @param message トーストに表示するメッセージ
   * @memberOf ToastService
   */
  public success(message: string): void {
    this.snackBar.open(message, this.actionMessage, {
      // duration: 5000,
      panelClass: ['green-snackbar'],
    });
  }

  public error(message: string): void {
    this.snackBar.open(message, this.actionMessage, {
      duration: this.duration,
      panelClass: ['red-snackbar'],
    });
  }

  public open(message: string): void {
    this.snackBar.open(message, this.actionMessage, {
      duration: this.duration,
    });
  }
}

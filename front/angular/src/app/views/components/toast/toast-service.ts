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

  constructor(private snackBar: MatSnackBar) {}

  /**
   * トーストを表示する
   *
   * @param message トーストに表示するメッセージ
   * @param duration トーストの表示時間
   * @memberOf ToastService
   */
  public success(
    message: string,
    duration: ToastDuration = ToastDuration.Default
  ): void {
    this.snackBar.open(message, this.actionMessage, {
      duration: this.getDuration(duration),
      panelClass: ['green-snackbar'],
    });
  }

  public error(
    message: string,
    duration: ToastDuration = ToastDuration.Default
  ): void {
    this.snackBar.open(message, this.actionMessage, {
      duration: this.getDuration(duration),
      panelClass: ['red-snackbar'],
    });
  }

  public open(
    message: string,
    duration: ToastDuration = ToastDuration.Default
  ): void {
    this.snackBar.open(message, this.actionMessage, {
      duration: this.getDuration(duration),
    });
  }

  private getDuration(d: ToastDuration): number | undefined {
    switch (d) {
      case ToastDuration.Short:
        return 3_000;
      case ToastDuration.Default:
        return 5_000;
      case ToastDuration.Long:
        return 10_000;
      case ToastDuration.Infinite:
        return undefined;
    }
  }
}

export enum ToastDuration {
  Short = 'Short',
  Default = 'Default',
  Long = 'Long',
  Infinite = 'Infinite',
}

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { EmailAddress } from '../../models/email/email-address';
import { Password } from '../../models/password/password';
import { config } from '../../config';
import { catchError, Observable, of } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class AuthService {
  constructor(private http: HttpClient) {}

  login(email: EmailAddress, password: Password): Promise<void> {
    return this.http
      .post(`${config.apiHost}/sign-in`, {
        email: email.value,
        password: password.value,
      })
      .pipe(catchError(this.handleError('login', [])))
      .forEach((_) => {});
  }

  /**
   * 失敗したHttp操作を処理します。
   * アプリを持続させます。
   *
   * @param operation - 失敗した操作の名前
   * @param result - observableな結果として返す任意の値
   */
  private handleError<T>(
    operation = 'operation',
    result?: T
  ): (error: any) => Observable<T> {
    return (error: any): Observable<T> => {
      // TODO: リモート上のロギング基盤にエラーを送信する
      console.error(error); // かわりにconsoleに出力

      // TODO: ユーザーへの開示のためにエラーの変換処理を改善する
      console.log(`${operation} failed: ${error.message}`);

      // 空の結果を返して、アプリを持続可能にする
      return of(result as T);
    };
  }
}

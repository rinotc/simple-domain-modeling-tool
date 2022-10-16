import { Injectable } from '@angular/core';
import {
  HttpErrorResponse,
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest,
} from '@angular/common/http';
import { catchError, Observable, throwError } from 'rxjs';
import { ToastService } from '../views/components/toast/toast-service';
import { ErrorResponse } from '../api/error/error-response';
import { Router } from '@angular/router';

@Injectable()
export class DefaultInterceptor implements HttpInterceptor {
  constructor(private toastService: ToastService, private router: Router) {}

  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    return next.handle(req).pipe(
      catchError((e) => {
        console.warn('😈HttpInterceptor😈', e);
        const errRes = e as HttpErrorResponse;
        const errorBody = errRes.error as ErrorResponse;
        switch (errRes.status) {
          case 400:
            this.toastService.error('不正なリクエストです');
            break;
          case 401:
            this.toastService.error('認証が必要です');
            if (errorBody.code === 'not.authenticated') {
              this.router.navigateByUrl('/login').then(() => {});
            }
            break;
          case 403:
            this.toastService.error('アクセスが禁止されています');
            break;
          case 404:
            this.toastService.error('対象が見つかりませんでした');
            break;
          case 408:
            this.toastService.error('リクエストがタイムアウトしました');
            break;
          case 409:
            this.toastService.error('リソースがコンフリクトしました');
            break;
          default:
            this.toastService.error('予期せぬエラーが発生しました');
        }

        return throwError(e);
      })
    );
  }
}

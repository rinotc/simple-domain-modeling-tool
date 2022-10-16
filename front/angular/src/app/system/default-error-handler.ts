import { ErrorHandler, Injectable } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class DefaultErrorHandler implements ErrorHandler {
  handleError(error: any): void {
    console.warn('😈ErrorHandler😈', error);
  }
}

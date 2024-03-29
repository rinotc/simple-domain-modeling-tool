import { Component, OnInit } from '@angular/core';
import { EmailAddress } from '../../../../models/email/email-address';
import { Password } from '../../../../models/password/password';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { requirement } from '../../../../dbc/dbc';
import { AuthService } from '../../../../api/auth/auth-service';
import {
  ToastDuration,
  ToastService,
} from '../../../components/toast/toast-service';
import { Router } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { ErrorResponse } from '../../../../api/error/error-response';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.scss'],
})
export class LoginPageComponent implements OnInit {
  hide: boolean = true;
  control: FormGroup = new FormGroup({});

  constructor(
    private authService: AuthService,
    private toastService: ToastService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.control = new FormGroup({
      email: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', [
        Validators.required,
        Validators.minLength(Password.MIN_LENGTH),
        Validators.maxLength(Password.MAX_LENGTH),
        Password.validator,
      ]),
    });
  }

  canSubmit(): boolean {
    return this.control.valid;
  }

  async clickLoginButton(): Promise<void> {
    requirement(this.canSubmit());
    const email: EmailAddress = new EmailAddress(this.control.value.email);
    const password: Password = new Password(this.control.value.password);
    await this.authService
      .login(email, password)
      .then(() => {
        this.toastService.success('ログインに成功しました');
        this.router.navigateByUrl('');
      })
      .catch((e) => {
        const httpErrorResponse = e as HttpErrorResponse;
        const error = httpErrorResponse.error as ErrorResponse;
        if (error.code === 'auth.signIn.notFound.account') {
          this.toastService.error(
            'アカウントが見つかりませんでした',
            ToastDuration.Infinite
          );
        } else if (error.code === 'auth.signIn.invalid.password') {
          this.toastService.error(
            'パスワードが違います',
            ToastDuration.Infinite
          );
        }
      });
  }
}

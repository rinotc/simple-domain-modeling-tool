import { Component, OnInit } from '@angular/core';
import { EmailAddress } from '../../../../models/email/email-address';
import { Password } from '../../../../models/password/password';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { requirement } from '../../../../dbc/dbc';
import { AuthService } from '../../../../api/auth/auth-service';
import { ToastService } from '../../../components/toast/toast-service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.scss'],
})
export class LoginPageComponent implements OnInit {
  hide: boolean = true;

  control: FormGroup;

  constructor(
    private authService: AuthService,
    private toastService: ToastService,
    private router: Router
  ) {
    this.control = new FormGroup({
      email: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', [Validators.required]),
    });
  }

  ngOnInit(): void {}

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
      .catch((_) => {
        this.toastService.error('ログインに失敗しました');
      });
  }
}

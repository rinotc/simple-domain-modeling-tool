import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { EmailAddress } from '../../models/email/email-address';
import { Password } from '../../models/password/password';
import { config } from '../../config';
import { lastValueFrom } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class AuthService {
  constructor(private http: HttpClient) {}

  login(email: EmailAddress, password: Password): Promise<Object> {
    const result = this.http.post(
      `${config.apiHost}/sign-in`,
      {
        email: email.value,
        password: password.value,
      },
      { withCredentials: true }
    );

    return lastValueFrom(result);
  }
}

import { Component } from '@angular/core';
import {User} from "./models/user/user";
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'angular-front';

  users: User[] = [];

  constructor(private http: HttpClient) {}

  ngOnInit() {
    this.http
      .get<{ data: User[] }>('http://localhost:9000/api/users')
      .subscribe((res) => {
          this.users = res.data;
      });
  }
}

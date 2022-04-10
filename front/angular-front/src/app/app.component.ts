import {Component} from '@angular/core';
import {UserService} from "./services/user/user.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {

  users$ = this.userService.users$;

  constructor(private userService: UserService) {}

  ngOnInit() {
    this.userService.fetchUsers();
  }
}

import {Component} from '@angular/core';
import {UserListUseCase} from "./services/user-list/user-list.usecase";
import {UserListFilter} from "./state/state";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {

  users$ = this.userListUseCase.users$;
  userListFilter$ = this.userListUseCase.filter$;

  constructor(private userListUseCase: UserListUseCase) {}

  ngOnInit() {
    this.userListUseCase.fetchUsers();
  }

  setUserListFilter(value: UserListFilter) {
    this.userListUseCase.setNameFilter(value.nameFilter);
  }
}

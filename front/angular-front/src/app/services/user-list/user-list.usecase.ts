import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {firstValueFrom, map, Observable} from "rxjs";
import {User} from "../../models/user/user";
import {Store} from "../../state/store/store";
import {UserApiService} from "../user-api/user-api.service";

@Injectable({
  providedIn: 'root'
})
export class UserListUseCase {

  get users$(): Observable<User[]> {
    return this.store
      .select(state => state.userList)
      .pipe(
        map(({ items, filter }) =>
          items.filter(user =>
            user.name.includes(filter.nameFilter)
          )
        )
      );
  }

  get filter$() {
    return this.store.select(state => state.userList.filter);
  }

  constructor(private userApi: UserApiService, private store: Store) {}

  async fetchUsers() {
    const users = await this.userApi.getAllUsers();

    this.store.update(state => ({
      ...state,
      userList: {
        ...state.userList,
        items: users
      }
    }));
  }

  setNameFilter(nameFilter: string) {
    this.store.update(state => ({
      ...state,
      userList: {
        ...state.userList,
        filter: {
          nameFilter
        }
      }
    }));
  }
}

import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {firstValueFrom, map, Observable} from "rxjs";
import {User} from "../../models/user/user";
import {Store} from "../../state/store/store";

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

  constructor(private http: HttpClient, private store: Store) {}

  async fetchUsers() {
    const users = await firstValueFrom(
      this.http.get< {data: User[] }>("http://localhost:9000/api/users")
        .pipe(map(res => res.data))
    );

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

import {Injectable} from "@angular/core";
import {User} from "../../models/user/user";
import {UserApiService} from "../user-api/user-api.service";
import {Store} from "../../state/store/store";
import {Observable} from "rxjs";

@Injectable({ providedIn: 'root'})
export class UserDetailUseCase {

  get user$(): Observable<User | null> {
    return this.store.select(state => state.userDetail.user);
  }

  constructor(private userApi: UserApiService, private store: Store) {}

  async fetchUser(userId: string) {
    this.store.update(state => ({
      ...state,
      userDetail: {
        ...state.userDetail,
        user: null
      }
    }));

    const user = await this.userApi.getUserById(userId)

    this.store.update(state => ({
      ...state,
      userDetail: {
        ...state.userDetail,
        user
      }
    }))
  }
}

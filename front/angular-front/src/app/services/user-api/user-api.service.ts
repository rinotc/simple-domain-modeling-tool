import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {firstValueFrom, map} from "rxjs";
import {User} from "../../models/user/user";
import {config} from "../../config";

interface ApiResponse<T> {
  data: T;
}

@Injectable({providedIn: 'root'})
export class UserApiService {
  constructor(private http: HttpClient) {}

  getAllUsers(): Promise<User[]> {
    return firstValueFrom(
      this.http
        .get<ApiResponse<User[]>>(`${config.apiHost}/users`)
        .pipe(map(resp => resp.data))
    )
  }

  getUserById(id: string) {
    return firstValueFrom(
      this.http
        .get<ApiResponse<User>>(`${config.apiHost}/users/${id}`)
        .pipe(map(resp => resp.data))
    );
  }
}


import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {BehaviorSubject, map, Observable} from "rxjs";
import {User} from "../../models/user/user";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private usersSubject = new BehaviorSubject<User[]>([]);

  get users$(): Observable<User[]> {
    return this.usersSubject.asObservable();
  }

  constructor(private http: HttpClient) {}

  fetchUsers(): void {
    this.http.get< {data: User[] }>("http://localhost:9000/api/users")
      .pipe(map(res => res.data))
      .subscribe(users => {
        this.usersSubject.next(users);
      });
  }
}

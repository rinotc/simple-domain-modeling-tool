import {User} from "../models/user/user";

export interface UserListFilter {
  nameFilter: string;
}

export interface State {
  userList: {
    items: User[];
    filter: UserListFilter;
  }

  userDetail: {
    user: User | null;
  }
}

export const initialState: State = {
  userList: {
    items: [],
    filter: {
      nameFilter: '',
    }
  },
  userDetail: {
    user: null
  }
};



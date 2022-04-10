import { TestBed } from '@angular/core/testing';

import { UserListUseCase } from './user-list.usecase';
import {HttpClientTestingModule} from "@angular/common/http/testing";

describe('UserListUsecase', () => {
  let service: UserListUseCase;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule,
      ]
    });
    service = TestBed.inject(UserListUseCase);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

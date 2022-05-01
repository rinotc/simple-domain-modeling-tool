import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BoundedContextsListPageComponent } from './bounded-contexts-list-page.component';

describe('ProjectListPageComponent', () => {
  let component: BoundedContextsListPageComponent;
  let fixture: ComponentFixture<BoundedContextsListPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BoundedContextsListPageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BoundedContextsListPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BoundedContextListComponentComponent } from './bounded-context-list-component.component';

xdescribe('ProjectListComponentComponent', () => {
  let component: BoundedContextListComponentComponent;
  let fixture: ComponentFixture<BoundedContextListComponentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [BoundedContextListComponentComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BoundedContextListComponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

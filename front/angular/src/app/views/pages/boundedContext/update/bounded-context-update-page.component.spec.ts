import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BoundedContextUpdatePageComponent } from './bounded-context-update-page.component';

xdescribe('BoundedContextUpdatePageComponent', () => {
  let component: BoundedContextUpdatePageComponent;
  let fixture: ComponentFixture<BoundedContextUpdatePageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [BoundedContextUpdatePageComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BoundedContextUpdatePageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BoundedContextDetailPageComponent } from './bounded-context-detail-page.component';

xdescribe('ProjectDetailComponent', () => {
  let component: BoundedContextDetailPageComponent;
  let fixture: ComponentFixture<BoundedContextDetailPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [BoundedContextDetailPageComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BoundedContextDetailPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

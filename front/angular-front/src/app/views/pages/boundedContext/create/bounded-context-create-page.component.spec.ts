import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BoundedContextCreatePageComponent } from './bounded-context-create-page.component';

xdescribe('ProjectCreateComponent', () => {
  let component: BoundedContextCreatePageComponent;
  let fixture: ComponentFixture<BoundedContextCreatePageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BoundedContextCreatePageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BoundedContextCreatePageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

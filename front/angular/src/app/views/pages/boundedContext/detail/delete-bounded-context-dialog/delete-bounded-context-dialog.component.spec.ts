import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeleteBoundedContextDialogComponent } from './delete-bounded-context-dialog.component';

xdescribe('DeleteBoundedContextDialogComponent', () => {
  let component: DeleteBoundedContextDialogComponent;
  let fixture: ComponentFixture<DeleteBoundedContextDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DeleteBoundedContextDialogComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DeleteBoundedContextDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

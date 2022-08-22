import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditDomainModelPageComponent } from './edit-domain-model-page.component';

describe('EditDomainModelPageComponent', () => {
  let component: EditDomainModelPageComponent;
  let fixture: ComponentFixture<EditDomainModelPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EditDomainModelPageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EditDomainModelPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

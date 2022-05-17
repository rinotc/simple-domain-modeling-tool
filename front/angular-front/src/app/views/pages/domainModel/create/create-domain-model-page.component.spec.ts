import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateDomainModelPageComponent } from './create-domain-model-page.component';

xdescribe('CreateDomainModelComponent', () => {
  let component: CreateDomainModelPageComponent;
  let fixture: ComponentFixture<CreateDomainModelPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CreateDomainModelPageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateDomainModelPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

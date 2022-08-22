import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DomainModelDetailPageComponent } from './domain-model-detail-page.component';

xdescribe('DomainModelDetailPageComponent', () => {
  let component: DomainModelDetailPageComponent;
  let fixture: ComponentFixture<DomainModelDetailPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DomainModelDetailPageComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DomainModelDetailPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

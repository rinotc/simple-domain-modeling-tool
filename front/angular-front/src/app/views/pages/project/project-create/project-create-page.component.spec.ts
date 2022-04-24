import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProjectCreatePageComponent } from './project-create-page.component';

xdescribe('ProjectCreateComponent', () => {
  let component: ProjectCreatePageComponent;
  let fixture: ComponentFixture<ProjectCreatePageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ProjectCreatePageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ProjectCreatePageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

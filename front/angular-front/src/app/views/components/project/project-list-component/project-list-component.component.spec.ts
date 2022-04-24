import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProjectListComponentComponent } from './project-list-component.component';

xdescribe('ProjectListComponentComponent', () => {
  let component: ProjectListComponentComponent;
  let fixture: ComponentFixture<ProjectListComponentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ProjectListComponentComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ProjectListComponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

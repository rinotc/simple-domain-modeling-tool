import {Component, OnInit} from '@angular/core';
import {Project} from "../../../../models/project/project";
import {ProjectRepository} from "../../../../models/project/project.repository";

@Component({
  selector: 'app-project-list-component',
  templateUrl: './project-list-component.component.html',
  styleUrls: ['./project-list-component.component.scss']
})
export class ProjectListComponentComponent implements OnInit {

  projects: Project[] = [];

  displayedColumns: string[] = ['project-alias', 'project-name', 'project-overview', 'project-detail']

  constructor(private projectRepository: ProjectRepository) {}

  ngOnInit(): void {
    this.projectRepository.getProjects().subscribe(projects =>
      this.projects = projects
    )
  }
}

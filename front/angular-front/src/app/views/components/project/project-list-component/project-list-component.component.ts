import {Component, OnInit} from '@angular/core';
import {BoundedContext} from "../../../../models/boundedContext/bounded-context";
import {BoundedContextRepository} from "../../../../models/boundedContext/bounded-context.repository";

@Component({
  selector: 'app-project-list-component',
  templateUrl: './project-list-component.component.html',
  styleUrls: ['./project-list-component.component.scss']
})
export class ProjectListComponentComponent implements OnInit {

  projects: BoundedContext[] = [];

  displayedColumns: string[] = ['project-alias', 'project-name', 'project-overview', 'project-detail']

  constructor(private projectRepository: BoundedContextRepository) {}

  ngOnInit(): void {
    this.projectRepository.getAll().subscribe(projects => {
      this.projects = projects
    });
  }
}

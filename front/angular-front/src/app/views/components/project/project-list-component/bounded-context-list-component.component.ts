import {Component, OnInit} from '@angular/core';
import {BoundedContext} from "../../../../models/boundedContext/bounded-context";
import {BoundedContextRepository} from "../../../../models/boundedContext/bounded-context.repository";

@Component({
  selector: 'app-project-list-component',
  templateUrl: './bounded-context-list-component.component.html',
  styleUrls: ['./bounded-context-list-component.component.scss']
})
export class BoundedContextListComponentComponent implements OnInit {

  boundedContexts: BoundedContext[] = [];

  displayedColumns: string[] = ['alias', 'name', 'overview', 'detail']

  constructor(private projectRepository: BoundedContextRepository) {}

  ngOnInit(): void {
    this.projectRepository.getAll().subscribe(projects => {
      this.boundedContexts = projects
    });
  }
}

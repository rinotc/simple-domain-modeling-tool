import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-project-create-page',
  templateUrl: './project-create-page.component.html',
  styleUrls: ['./project-create-page.component.scss']
})
export class ProjectCreatePageComponent implements OnInit {

  inputProjectName: string = '';
  inputProjectAlias: string = '';
  inputProjectOverview: string = '';


  constructor() { }

  ngOnInit(): void {
  }

  onSubmit(): void {
    console.log(`${this.inputProjectName}, ${this.inputProjectAlias}, ${this.inputProjectOverview}`)
  }
}

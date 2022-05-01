import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {HeaderService} from "../../../../store/title/header.service";

@Component({
  selector: 'app-project-detail',
  templateUrl: './bounded-context-detail-page.component.html',
  styleUrls: ['./bounded-context-detail-page.component.scss']
})
export class BoundedContextDetailPageComponent implements OnInit {

  constructor(
    private route: ActivatedRoute,
    private headerService: HeaderService
  ) {}

  ngOnInit(): void {
    this.headerService.update('aiueo')
  }
}

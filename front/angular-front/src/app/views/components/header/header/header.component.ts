import {Component, Input, OnInit} from '@angular/core';
import {HeaderQuery} from "../../../../store/title/header.query";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  @Input() title!: string;

  constructor(private titleQuery: HeaderQuery) { }

  ngOnInit(): void {}

  get nowContext() {
    const nc = this.titleQuery.nowContext;
    if (nc !== '') {
      return ' > ' + this.titleQuery.nowContext;
    }
    return '';
  }
}

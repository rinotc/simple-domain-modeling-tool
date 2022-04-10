import {Component, EventEmitter, Input, OnDestroy, OnInit, Output} from '@angular/core';
import {UserListFilter} from "../../../state/state";
import {FormBuilder, FormGroup} from "@angular/forms";
import {takeUntil} from "rxjs";

@Component({
  selector: 'app-user-list-filter-component',
  templateUrl: './user-list-filter-component.component.html',
  styleUrls: ['./user-list-filter-component.component.scss']
})
export class UserListFilterComponentComponent implements OnInit, OnDestroy {

  @Input() set value(value: UserListFilter) {
    this.setFormValue(value);
  }
  @Output() valueChanges = new EventEmitter<UserListFilter>();

  form: FormGroup;

  private onDestroy = new EventEmitter();

  constructor(private fb: FormBuilder) {
    this.form = this.fb.group({
      nameFilter: ['']
    });
  }

  ngOnInit(): void {
    this.form.valueChanges.pipe(takeUntil(this.onDestroy)).subscribe(value => {
      this.valueChanges.emit(value);
    });
  }

  ngOnDestroy(): void {
    this.onDestroy.next({});
  }

  private setFormValue(value: UserListFilter) {
    this.form.setValue(value, { emitEvent: false });
  }
}

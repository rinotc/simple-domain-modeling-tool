import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { BoundedContextDetailPageComponent } from '../bounded-context-detail-page.component';
import { requirement } from '../../../../../dbc/dbc';

export interface DialogInput {
  boundedContextName: string;
  boundedContextAlias: string;
}

@Component({
  selector: 'app-delete-bounded-context-dialog',
  templateUrl: './delete-bounded-context-dialog.component.html',
  styleUrls: ['./delete-bounded-context-dialog.component.scss'],
})
export class DeleteBoundedContextDialogComponent implements OnInit {
  inputBoundedContextAlias: string = '';
  constructor(
    public dialogRef: MatDialogRef<BoundedContextDetailPageComponent>,
    @Inject(MAT_DIALOG_DATA) public input: DialogInput
  ) {}

  ngOnInit(): void {}

  onNoClick(): void {
    this.dialogRef.close();
  }

  get canDelete(): boolean {
    return this.inputBoundedContextAlias === this.input.boundedContextAlias;
  }

  onClickDelete() {
    requirement(this.canDelete, 'can delete only same alias input.');
    console.log('削除');
  }
}

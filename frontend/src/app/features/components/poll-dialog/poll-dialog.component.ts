import { Component, Inject, ViewEncapsulation } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Poll } from '../../models/poll.interface';
import { MatRadioModule } from '@angular/material/radio';
import { NgFor } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatDialogModule} from '@angular/material/dialog';
import { AxiosService } from '../../../shared/services/axios.service.service';
import { ApiResponse } from '../../../shared/models/api.resposne.interface';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-poll-dialog',
  standalone: true,
  imports: [NgFor, MatRadioModule, MatButtonModule, MatDialogModule],
  templateUrl: './poll-dialog.component.html',
  styleUrl: './poll-dialog.component.css',
  encapsulation: ViewEncapsulation.None,
})
export class PollDialogComponent {
  apiResponse: ApiResponse = {
    success: false,
    message: '',
    data: null
  };

  selectedOptionId: number | null = null;

  constructor(
    public dialogRef: MatDialogRef<PollDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Poll,
    private axiosService: AxiosService,
    private toast: ToastrService
  ) {}

  onNoClick(): void {
    this.dialogRef.close();
  }

  selectOption(option: any): void {
    this.selectedOptionId = option.id;
  }

  submitAnswer(): void {
    if (this.selectedOptionId !== null) {

      this.axiosService.request('POST', `/polls/${this.data.id}/vote/${this.selectedOptionId}`, null).then((response) => {
        this.apiResponse = response.data;
        if (this.apiResponse.success) {
          this.toast.success("Vote added successfully!", "Success!")

          this.dialogRef.close();
        }
      });
    }
  }
}

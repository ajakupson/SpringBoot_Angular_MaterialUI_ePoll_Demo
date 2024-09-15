import { NgFor, NgIf } from '@angular/common';
import { Component } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { FormsModule } from '@angular/forms';
import { ApiResponse } from '../../../shared/models/api.resposne.interface';
import { AxiosService } from '../../../shared/services/axios.service.service';
import { ToastrService } from 'ngx-toastr';
import { PollOption } from '../../models/poll.option.interface';
import { Poll } from '../../models/poll.interface';

@Component({
  selector: 'app-create-poll-dialog',
  standalone: true,
  imports: [
    NgIf, 
    NgFor, 
    MatButtonModule, 
    MatDialogModule,
    MatFormFieldModule,
    MatInputModule,
    MatIconModule,
    FormsModule
  ],
  templateUrl: './create-poll-dialog.component.html',
  styleUrl: './create-poll-dialog.component.css'
})
export class CreatePollDialogComponent {
  pollTitle: string = '';
  pollOptions: string[] = [''];

  apiResponse: ApiResponse = {
    success: false,
    message: '',
    data: null
  };

  constructor(
    public dialogRef: MatDialogRef<CreatePollDialogComponent>,
    private axiosService: AxiosService,
    private toast: ToastrService
  ) {}

  trackByFn(index: number, item: any): number {
    return index;
  }

  addOption() {
    this.pollOptions.push('');
  }

  removeOption(index: number) {
    this.pollOptions.splice(index, 1);
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  submitPoll(): void {
    if (this.pollTitle && this.pollOptions.some(option => option.trim() !== '')) {
      let newPoll: Poll = {
        id: null,
        title: this.pollTitle,
        options: []
      }

      this.pollOptions.forEach(po => {
        if (po.trim() !== '') {
          const pollOption: PollOption = {
            id: null,
            title: po,
            votes: 0
          };

          newPoll.options.push(pollOption);
        }
      });
    

      this.axiosService.request('POST', '/polls/add', newPoll).then((response) => {
        this.apiResponse = response.data;
        if (this.apiResponse.success) {
          this.toast.success("Poll added successfully!", "Success!");
  
          this.dialogRef.close(this.apiResponse.data);
          return;
        }
  
        this.toast.error("Error adding new poll!", "Error!");
      });
    }
  }
}

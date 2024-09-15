import { Component, ViewChild, OnInit, OnDestroy } from '@angular/core';
import { AxiosService } from '../../../shared/services/axios.service.service';
import { ApiResponse } from '../../../shared/models/api.resposne.interface';
import { Poll } from '../../models/poll.interface';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatSort, MatSortModule } from '@angular/material/sort';
import { MatFormFieldModule } from '@angular/material/form-field';
import { NgFor, NgIf } from '@angular/common';
import { MatInputModule } from '@angular/material/input';
import { MatDialog } from '@angular/material/dialog';
import { PollDialogComponent } from '../poll-dialog/poll-dialog.component';
import { MatButtonModule } from '@angular/material/button';
import { CreatePollDialogComponent } from '../create-poll-dialog/create-poll-dialog.component';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-poll-list',
  standalone: true,
  imports: [
    MatTableModule, 
    MatPaginatorModule, 
    MatSortModule, 
    MatFormFieldModule, 
    MatInputModule,
    NgIf,
    NgFor,
    MatButtonModule 
  ],
  templateUrl: './poll-list.component.html',
  styleUrls: ['./poll-list.component.css']
})
export class PollListComponent implements OnInit, OnDestroy {

  apiResponse: ApiResponse = {
    success: false,
    message: '',
    data: null
  };

  polls: Poll[] = [];
  displayedColumns: string[] = ['id', 'title', 'actions'];
  dataSource = new MatTableDataSource<Poll>();

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(
    private axiosService: AxiosService, 
    private dialog: MatDialog,
    private toast: ToastrService) {}

  ngOnInit() {
    this.getListOfPolls();
  }

  getListOfPolls() {
    this.axiosService.request("GET", "/polls", null).then((response) => {
      this.apiResponse = response.data;
      if (this.apiResponse.success) {
        this.polls = this.apiResponse.data;
        this.dataSource.data = this.polls;
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
      }
    });
  }

  applyFilter(event: Event): void {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  openPollDialog(poll: Poll): void {

    this.axiosService.request('GET', `/polls/${poll.id}`, null).then((response) => {
      this.apiResponse = response.data;
      if (this.apiResponse.success) {
        this.dialog.open(PollDialogComponent, {
          width: 'auto',
          height: 'auto',
          data: this.apiResponse.data
        });

        return;
      }

      this.toast.error(this.apiResponse.message, "Error!");
    });
  }

  openCreatePollDialog(): void {
    const dialogRef = this.dialog.open(CreatePollDialogComponent, {
      width: 'auto',
      height: 'auto'
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.polls.push(result);
        this.dataSource.data = [...this.polls];
      }
    });
  }

  ngOnDestroy(): void {}
}
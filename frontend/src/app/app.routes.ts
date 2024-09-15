import { Routes } from '@angular/router';
import { PollListComponent } from './features/components/poll-list/poll-list.component';

export const routes: Routes = [
    { path: '', redirectTo: '/poll-list', pathMatch: 'full' },
    { path: 'poll-list', component: PollListComponent },
];

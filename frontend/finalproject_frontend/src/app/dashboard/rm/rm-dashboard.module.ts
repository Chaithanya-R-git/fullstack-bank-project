import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { ReactiveFormsModule } from '@angular/forms';
import { RmDashboardComponent } from './rm-dashboard.component';
import { CreditRequestComponent } from './credit-request/credit-request.component';

const routes: Routes = [
  { path: '', component: RmDashboardComponent }
];

@NgModule({
  declarations: [RmDashboardComponent,CreditRequestComponent],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    RouterModule.forChild(routes)
  ]
})
export class RmDashboardModule {}

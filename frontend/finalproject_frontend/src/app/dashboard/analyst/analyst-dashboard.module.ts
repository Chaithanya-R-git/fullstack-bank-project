import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { AnalystDashboardComponent } from './analyst-dashboard.component';

const routes: Routes = [
  {
    path: '',          // 🔥 THIS IS MANDATORY
    component: AnalystDashboardComponent
  }
];

@NgModule({
  declarations: [AnalystDashboardComponent],
  imports: [
    CommonModule,
    RouterModule.forChild(routes)
  ]
})
export class AnalystDashboardModule {}

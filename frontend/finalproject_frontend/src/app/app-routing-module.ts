import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { Login } from './auth/login/login';
import { AuthGuard } from './auth/auth-guard';
import { RoleGuard } from './auth/role.guard';

const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },

  { path: 'login', component: Login },

  {
    path: 'rm',
    canActivate: [AuthGuard, RoleGuard],
    data: { role: 'RM' },
    loadChildren: () =>
      import('./dashboard/rm/rm-dashboard.module')
        .then(m => m.RmDashboardModule)
  },
{
  path: 'admin',
  canActivate: [AuthGuard, RoleGuard],
  data: { role: 'ADMIN' },
  loadChildren: () =>
    import('./dashboard/admin/admin-dashboard.module')
      .then(m => m.AdminDashboardModule)
},


  {
    path: 'analyst',
    canActivate: [AuthGuard, RoleGuard],
    data: { role: 'ANALYST' },
    loadChildren: () =>
      import('./dashboard/analyst/analyst-dashboard.module')
        .then(m => m.AnalystDashboardModule)
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}

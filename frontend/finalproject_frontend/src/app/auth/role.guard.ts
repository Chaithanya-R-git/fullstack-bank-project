import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class RoleGuard implements CanActivate {

  constructor(private router: Router) {}

  canActivate(route: ActivatedRouteSnapshot): boolean {
    const expectedRole = route.data['role'];   // RM / ANALYST
    const actualRole = localStorage.getItem('role');

    console.log('Expected:', expectedRole);
    console.log('Actual:', actualRole);

    if (actualRole === expectedRole) {
      return true;
    }

    // ❌ block navigation
    this.router.navigate(['/login']);
    return false;
  }
}

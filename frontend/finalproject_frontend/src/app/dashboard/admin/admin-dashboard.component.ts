import { Component, OnInit ,AfterViewInit } from '@angular/core';
// import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { ChangeDetectorRef } from '@angular/core';

@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './admin-dashboard.component.html',
  standalone:false,
  styleUrls: ['./admin-dashboard.component.css']
})
export class AdminDashboardComponent implements OnInit {

  users: any[] = [];

  newUser = {
    username: '',
    email: '',
    password: '',
    role: 'RM'
  };

  constructor(
    private http: HttpClient,
    private router: Router,
      private cdr: ChangeDetectorRef

  ) {}

  ngOnInit(): void {
  this.loadUsers();
}


getAuthHeaders() {
  const token = localStorage.getItem('token');
  return {
    headers: new HttpHeaders({
      Authorization: `Bearer ${token}`
    })
  };
}

loadUsers() {
  this.http
    .get<any[]>(
      'http://localhost:9090/api/admin/users',
      this.getAuthHeaders()
    )
    .subscribe({
      next: data => {
        console.log('ADMIN USERS:', data);
        this.users = data;
        this.cdr.detectChanges();

      },
      error: err => {
        console.error('ADMIN LOAD USERS FAILED', err);
      }
    });
}


toggleStatus(user: any) {
  this.http
    .put(
      `http://localhost:9090/api/admin/users/${user.id}/status?active=${!user.active}`,
      {},
      this.getAuthHeaders()
    )
    .subscribe(() => {
      user.active = !user.active;
    });
}

createUser() {
  this.http
    .post(
      'http://localhost:9090/api/auth/register',
      this.newUser,
      this.getAuthHeaders()
    )
    .subscribe(() => {
      alert('User created');
      this.newUser = { username: '', email: '', password: '', role: 'RM' };
      this.loadUsers();
    });
}

  logout() {
    localStorage.clear();
    this.router.navigate(['/login']);
  }
}

import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { ChangeDetectorRef } from '@angular/core';

@Component({
  selector: 'app-analyst-dashboard',
  standalone:false,
  templateUrl: './analyst-dashboard.component.html'
})
export class AnalystDashboardComponent implements OnInit {

  creditRequests: any[] = [];
  total: number = 0;
  pending: number = 0;
  approved: number = 0;
  rejected: number = 0;
constructor(
  private http: HttpClient,
  private cdr: ChangeDetectorRef
) {}


ngOnInit(): void {
  setTimeout(() => {
    this.loadCreditRequests();
  }, 0);
}

loadCreditRequests() {
  const token = localStorage.getItem('token');

  if (!token) {
    console.warn('Token not available yet, retrying...');
    setTimeout(() => this.loadCreditRequests(), 100);
    return;
  }

  const headers = new HttpHeaders({
    Authorization: `Bearer ${token}`
  });

    this.http
      .get<any[]>('http://localhost:9090/api/credit-requests/all', { headers })
      .subscribe({
        next: (data) => {
          console.log('CREDIT REQUESTS FROM BACKEND:', data); // 🔥 IMPORTANT
          this.creditRequests = data;
          this.calculateSummary();
          
  this.cdr.detectChanges();
        },
        error: (err) => {
          console.error('Failed to load credit requests', err);
        }
      });
      
  }
    calculateSummary() {
    this.total = this.creditRequests.length;
    this.pending = this.creditRequests.filter(r => r.status === 'PENDING').length;
    this.approved = this.creditRequests.filter(r => r.status === 'APPROVED').length;
    this.rejected = this.creditRequests.filter(r => r.status === 'REJECTED').length;
  }
  loadAllCreditRequests() {
  this.http.get<any[]>(
    'http://localhost:9090/api/credit-requests/all'
  ).subscribe({
    next: data => {
      this.creditRequests = data;

      this.total = data.length;
      this.pending = data.filter(r => r.status === 'PENDING').length;
      this.approved = data.filter(r => r.status === 'APPROVED').length;
      this.rejected = data.filter(r => r.status === 'REJECTED').length;
    },
    error: err => console.error(err)
  });
}


  updateStatus(id: string, status: string) {
    const token = localStorage.getItem('token');

    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`
    });

    this.http
      .put(
        `http://localhost:9090/api/credit-requests/${id}`,
        { status, remarks: '' },
        { headers }
      )
      .subscribe({
        next: () => {
          alert(`Request ${status}`);
          this.loadCreditRequests(); // 🔄 reload table
        },
        error: err => {
          console.error('Update failed', err);
        }
      });
  }
  logout() {
  localStorage.removeItem('token');
  localStorage.removeItem('role');
  window.location.href = '/login';
}

}

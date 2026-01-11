import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { CreditRequestService } from '../../services/credit-request.service';
import { ChangeDetectorRef } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-rm-dashboard',
  templateUrl: './rm-dashboard.component.html',
  standalone:false,
  styleUrls: ['./rm-dashboard.component.css']
})
export class RmDashboardComponent implements OnInit {

  clientForm!: FormGroup;
  clients: any[] = [];
  creditRequests: any[] = [];

totalClients = 0;
totalRequests = 0;
pendingRequests = 0;

  constructor(private fb: FormBuilder,private creditRequestService: CreditRequestService, private http: HttpClient,
  private router: Router,
  private cdr: ChangeDetectorRef) {}

  ngOnInit(): void {
    this.clientForm = this.fb.group({
      companyName: ['', Validators.required],
      industry: ['', Validators.required],
      address: ['', Validators.required],
      contactName: ['', Validators.required],
      contactEmail: ['', [Validators.required, Validators.email]],
      contactPhone: ['', Validators.required],
      annualTurnover: [0, Validators.required],
      documentsSubmitted: [false]
    });

    this.loadClients();
     //   this.loadCreditRequests();   
         this.loadMyCreditRequests(); 

  }

  createClient() {
    this.http.post(
      'http://localhost:9090/api/rm/clients',
      this.clientForm.value,
      {
        headers: {
          Authorization: `Bearer ${localStorage.getItem('token')}`
        }
      }
    ).subscribe({
      next: () => {
        alert('Client created successfully');
        this.clientForm.reset();
        this.loadClients();
      },
      error: err => console.error(err)
    });
  }
 loadMyCreditRequests() {
  this.http.get<any[]>(
    'http://localhost:9090/api/credit-requests',
    {
      headers: {
        Authorization: `Bearer ${localStorage.getItem('token')}`
      }
    }
  ).subscribe({
    next: data => {
      this.totalRequests = data.length;
      this.pendingRequests = data.filter(r => r.status === 'PENDING').length;

      // 🔥 FORCE UI UPDATE
      this.cdr.detectChanges();
    },
    error: err => console.error(err)
  });
}



loadClients() {
  this.http.get<any[]>(
    'http://localhost:9090/api/rm/clients',
    {
      headers: {
        Authorization: `Bearer ${localStorage.getItem('token')}`
      }
    }
  ).subscribe({
    next: data => {
      this.clients = data;
      this.totalClients = data.length;

      // 🔥 FORCE UI UPDATE
      this.cdr.detectChanges();
    },
    error: err => console.error(err)
  });
}

// loadCreditRequests() {
//   this.http.get<any[]>(
//     'http://localhost:8080/api/credit-requests'
//   ).subscribe({
//     next: data => this.creditRequests = data,
//     error: err => console.error(err)
//   });
// }
logout() {
  localStorage.removeItem('token');
  localStorage.removeItem('role');
  window.location.href = '/login';
}



}

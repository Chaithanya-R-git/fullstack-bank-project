import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CreditRequestService {

  private baseUrl = 'http://localhost:9090/api/credit-requests';

  constructor(private http: HttpClient) {}

  private getHeaders(): HttpHeaders {
    const token = localStorage.getItem('token');
    return new HttpHeaders({
      Authorization: `Bearer ${token}`
    });
  }

  getAllCreditRequests(): Observable<any[]> {
    return this.http.get<any[]>(this.baseUrl, {
      headers: this.getHeaders()
    });
  }
}

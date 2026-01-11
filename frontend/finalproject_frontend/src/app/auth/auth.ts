import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
@Injectable({
  providedIn: 'root'
})
export class Auth {

  private API = 'http://localhost:9090/api/auth';

  constructor(private http: HttpClient) {}

  login(data: any) {
    return this.http.post(`${this.API}/login`, data);
  }
}

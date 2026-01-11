import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navbar',
  standalone:false,
  templateUrl: './navbar.html',
  styleUrls: ['./navbar.css']
})
export class NavbarComponent implements OnInit {

  role: string | null = null;

  constructor(private router: Router) {}

  ngOnInit(): void {
    this.role = localStorage.getItem('role');
  }

  logout() {
    localStorage.clear();
    this.router.navigate(['/login']);
  }
}

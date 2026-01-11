import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Auth } from '../auth';

@Component({
  selector: 'app-login',
  templateUrl: './login.html',
  standalone:false,
  styleUrls: ['./login.css']
})
export class Login implements OnInit {

  loginForm!: FormGroup;

  constructor(
    private fb: FormBuilder,
    private auth: Auth,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loginForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

login() {
  this.auth.login(this.loginForm.value).subscribe({
    next: (res: any) => {
      localStorage.setItem('token', res.token);
      localStorage.setItem('role', res.role);

      console.log('Logged in role:', res.role);

     if (res.role === 'ADMIN') {
  this.router.navigate(['/admin']);
} else if (res.role === 'ANALYST') {
  this.router.navigate(['/analyst']);
} else {
  this.router.navigate(['/rm']);
}

    },
    error: () => alert('Invalid credentials')
  });
}


}

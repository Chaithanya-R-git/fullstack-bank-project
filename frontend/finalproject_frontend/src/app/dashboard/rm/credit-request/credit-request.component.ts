import { Component, Input } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-credit-request',
  standalone:false,
  templateUrl: './credit-request.component.html'
})
export class CreditRequestComponent {

  @Input() clientId!: string;
  creditForm: FormGroup;

  constructor(private fb: FormBuilder, private http: HttpClient) {
    this.creditForm = this.fb.group({
      requestAmount: ['', Validators.required],
      tenureMonths: ['', Validators.required],
      purpose: ['', Validators.required],
      remarks: ['']
    });
  }

  submitCreditRequest() {
    const payload = {
      clientId: this.clientId,
      requestAmount: this.creditForm.value.requestAmount,
      tenureMonths: this.creditForm.value.tenureMonths,
      purpose: this.creditForm.value.purpose,
      status: 'PENDING',
      remarks: this.creditForm.value.remarks
    };

    this.http.post(
      'http://localhost:9090/api/credit-requests',
      payload,
      {
        headers: {
          Authorization: `Bearer ${localStorage.getItem('token')}`,
          'Content-Type': 'application/json'
        }
      }
    ).subscribe({
      next: () => {
        alert('Credit request submitted');
        this.creditForm.reset();
      },
      error: err => {
        console.error(err);
        alert('Error submitting request');
      }
    });
  }
}

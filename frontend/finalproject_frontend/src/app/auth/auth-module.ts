import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { Login } from './login/login';

@NgModule({
  declarations: [Login],
  imports: [
    CommonModule,
    ReactiveFormsModule
  ]
})
export class AuthModule {}

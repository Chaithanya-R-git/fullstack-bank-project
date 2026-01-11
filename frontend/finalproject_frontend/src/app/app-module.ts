import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { RouterModule } from '@angular/router';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { App } from './app';
import { AppRoutingModule } from './app-routing-module';
import { AuthModule } from './auth/auth-module';
import { FormsModule } from '@angular/forms';
import { AuthInterceptor } from './auth/jwt.interceptor';
import { JwtInterceptor } from './core/jwt-interceptor';
import { NavbarComponent } from './core/navbar/navbar';
@NgModule({
  providers: [
  {
    provide: HTTP_INTERCEPTORS,
    useClass: AuthInterceptor,
    multi: true
  }
],

  declarations: [
    App,
    NavbarComponent
  ],
  imports: [
    BrowserModule,
    ReactiveFormsModule,
    HttpClientModule,
    RouterModule, 
    FormsModule,      // ✅ REQUIRED for router-outlet
    AppRoutingModule,
    AuthModule
  ],
  bootstrap: [App]
})
export class AppModule { }

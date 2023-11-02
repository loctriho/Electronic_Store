import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { FormGroup, FormControl } from '@angular/forms';
import { Router } from '@angular/router';
import { Address } from '../models/address.model';

interface LoginResponse {
  email: string;
  first_name: string;
  last_name: string;
  address: Address;
  csrfToken: string;
}

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  @Output() userLoggedIn: EventEmitter<void> = new EventEmitter<void>();

  serverUrl = 'http://localhost:8080'; 
  googleLoginUrl = `${this.serverUrl}/oauth2/authorization/google`;
  facebookLoginUrl = `${this.serverUrl}/oauth2/authorization/facebook`;
  loginForm!: FormGroup; 
  loginError: boolean = false;  // <-- Add this line

  constructor(private httpClient: HttpClient, private router: Router) { }

  ngOnInit(): void {
    this.loginForm = new FormGroup({
      username: new FormControl(''),
      password: new FormControl(''),
    });
  }
  
  onSubmit(): void {
    const usernameControl = this.loginForm.get('username');
    const passwordControl = this.loginForm.get('password');
    
    // Check that the form controls exist before trying to access their value
    if (usernameControl && passwordControl) {
      const username = usernameControl.value;
      const password = passwordControl.value;
  
      const endpointUrl = `${this.serverUrl}/login`;
  
      const authHeader = 'Basic ' + btoa(`${username}:${password}`);
      const authHeaders = new HttpHeaders({ 'Authorization': authHeader });
      this.httpClient.post<LoginResponse>(endpointUrl, null, { headers: authHeaders, withCredentials: true }).subscribe(
        (response: LoginResponse) => {
          console.log(response);
          // Store CSRF token and user info in localStorage
          localStorage.setItem('csrfToken', response.csrfToken);
          localStorage.setItem('username', username);
          localStorage.setItem('password', password);
          localStorage.setItem('email', response.email);
          localStorage.setItem('first_name', response.first_name);
          localStorage.setItem('last_name', response.last_name);


  
          // Store address info in localStorage
          if (response.address) {
            localStorage.setItem('city', response.address.city || '');
            localStorage.setItem('streetNo', response.address.street || '');
            localStorage.setItem('apartment', response.address.apartment || '');
            localStorage.setItem('zipCode', response.address.zipCode || '');
            localStorage.setItem('state', response.address.state || '');

          }
  
          console.log('Login Successful:', response);
          this.userLoggedIn.emit();
  
          // Check if the previous URL exists in localStorage
          const previousUrl = localStorage.getItem('previous_url');
          if (previousUrl) {
            localStorage.removeItem('previous_url');
            this.router.navigateByUrl(previousUrl);
          } else {
            this.router.navigate(['/']); // Navigate to the home page
          }
        },
        (error: any) => {
          if (error.status === 401) {
            console.error('Login Error: Invalid username or password');
            this.loginError = true;  // <-- Add this line
          } else {
            console.error('Login Error:', error);
          }
        }
      );
    }
  }

  signInWithProvider(provider: string) {
    let authorizationUrl;
    const angularRedirectUrl = window.location.href;
    const state = btoa(angularRedirectUrl);  // Base64 encode the original URL
    
    if (provider === 'google') {
      let redirectUri = `${this.serverUrl}/login/oauth2/code/google`;
      authorizationUrl = `${this.googleLoginUrl}?redirect_uri=${encodeURIComponent(redirectUri)}&state=${encodeURIComponent(state)}`;
    } else if (provider === 'facebook') {
      let redirectUri = `${this.serverUrl}/login/oauth2/code/facebook`;
      authorizationUrl = `${this.facebookLoginUrl}?redirect_uri=${encodeURIComponent(redirectUri)}&state=${encodeURIComponent(state)}`;
    }
  
    if (authorizationUrl) {
      window.location.href = authorizationUrl;
    }
  }

  
}

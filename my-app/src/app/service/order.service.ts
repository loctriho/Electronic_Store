import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class OrderService {
  private orderAPI = 'http://localhost:8080/orders'; // Replace with your order API
  private updateAddressAPI = 'http://localhost:8080/user/update-address'; // Replace with your update address API

  constructor(private http: HttpClient) { }

  createOrder(orderData: any): Observable<any> {
    const headers = this.createHeaders();
    return this.http.post(this.orderAPI, orderData, { headers: headers, withCredentials: true });
  }
  

  updateAddress(addressData: any): Observable<any> {
    const headers = this.createHeaders();
    return this.http.post(this.updateAddressAPI, addressData, { headers: headers, withCredentials: true , responseType: 'text' });
  }

  private createHeaders(): HttpHeaders {
    const csrfToken = this.getCSRFToken();
    const username = localStorage.getItem('username');
    const password = localStorage.getItem('password');
    console.log("username:" + username)
    console.log("password:" + password);
    console.log("token:" + csrfToken);
    let headers = new HttpHeaders();
    if(csrfToken) {
      headers = headers.append('x-csrf-token', csrfToken);
    }

    if(username && password) {
      headers = headers.append('Authorization', 'Basic ' + btoa(username + ':' + password));
    }

    return headers;
  }

  private getCSRFToken(): string | null {
    return localStorage.getItem('csrfToken');
  }
}

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private messageSource = new BehaviorSubject<any>('');
  currentMessage = this.messageSource.asObservable();

  constructor(private http: HttpClient) { }

  createUserForRegistration(user: any) {
    // replace with your actual API endpoint
    return this.http.post('http://localhost:8080/register', user);  }

  changeMessage(message: any) {
    this.messageSource.next(message);
  }
}




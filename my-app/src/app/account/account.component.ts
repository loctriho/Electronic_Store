import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';

@Component({
  selector: 'app-account',
  templateUrl: './account.component.html',
  styleUrls: ['./account.component.css']
})
export class AccountComponent implements OnInit {
  personalInfoForm!: FormGroup;
  changePasswordForm!: FormGroup;  // Add this line
  pastOrders: any[] = []; // Use the appropriate type for your orders


  selectedSection = 'Personal Info'; // Set initial value
  showPersonalInfo = true;
  showChangePassword = false;
  showPastOrders = false;
  successMessage: string | null = null; // Add this line
  passwordUpdateMessage: string | null = null;

  

  constructor(private formBuilder: FormBuilder, private http: HttpClient) { }

  ngOnInit() {
    this.personalInfoForm = this.formBuilder.group({
      username: ['', Validators.required],
      first_name: ['', Validators.required],
      last_name: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      address: this.formBuilder.group({ // Add address group
        city: ['', Validators.required],
        street: ['', Validators.required],
        apartment: ['', Validators.required],
        state: ['', Validators.required],
        zipCode: ['', Validators.required]
      })
    });
    this.changePasswordForm = this.formBuilder.group({
      oldPassword: ['', Validators.required],
      newPassword: ['', [Validators.required, Validators.minLength(8)]],
      confirmPassword: ['', Validators.required]
    }, { validators: this.passwordsMatch });  // Add the validator here


    this.loadPersonalInfo();


    

    
  }
  
passwordsMatch(formGroup: FormGroup): ValidationErrors | null {
  const newPasswordControl = formGroup.get('newPassword');
  const confirmPasswordControl = formGroup.get('confirmPassword');

  if (newPasswordControl && confirmPasswordControl) {
    const newPassword = newPasswordControl.value;
    const confirmPassword = confirmPasswordControl.value;
    return newPassword === confirmPassword ? null : { mismatch: true };
  }
  
  return null;
}

  displaySection(section: string) {
    this.showPersonalInfo = false;
    this.showChangePassword = false;
    this.showPastOrders = false;

    switch (section) {
      case 'personal-info':
        this.selectedSection = 'Personal Info';
        this.showPersonalInfo = true;
        break;
      case 'change-password':
        this.selectedSection = 'Change Password';
        this.showChangePassword = true;
        break;
      case 'past-orders':
        this.selectedSection = 'Past Orders';
        this.showPastOrders = true;
        break;
      default:
        console.warn(`Unknown section: ${section}`);
    }
  }

  private loadPersonalInfo() {
    const token = this.getCSRFToken();

    if (token) {
      const headers = new HttpHeaders({
        'x-csrf-token': token
      });

      this.http.get('http://localhost:8080/user/getUserData', { headers: headers, withCredentials: true }).subscribe((data: any) => {
   


        this.personalInfoForm.patchValue({
          username: data.username,
          first_name: data.first_name,
          last_name: data.last_name,
          email: data.email,
          address: data.address // Set address values
        });
      });
    }
  }

  getCSRFToken(): string | null {
    return localStorage.getItem('csrfToken');
  }

  onSubmitPersonalInfo() {
    if (this.personalInfoForm.valid) {
      const token = this.getCSRFToken();
  
      if (token) {
        const headers = new HttpHeaders({
          'Content-Type': 'application/json',
          'x-csrf-token': token
        });
  
        this.http.post('http://localhost:8080/user/edit', this.personalInfoForm.value, { headers: headers, withCredentials: true, responseType: 'text' })
          .subscribe({
            next: response => {
              console.log('Update successful', response);
              this.successMessage = 'Updated successfully'; // Add this line
            },
            error: error => {
              console.error('There was an error!', error);
              this.successMessage = null; // Add this line
            }
          });
      }
    } else {
      console.error("Form is invalid!");
    }
  }
  
  

  onChangePassword() {
    if (this.changePasswordForm.valid) {
      const oldPassword = this.changePasswordForm.get('oldPassword')?.value;
      const newPassword = this.changePasswordForm.get('newPassword')?.value;
  
      const headers = new HttpHeaders({
        'Content-Type': 'application/json',
        'x-csrf-token': this.getCSRFToken() || ''
      });
  
      // Send passwords in the request body instead of query parameters
      const body = {
        oldPassword: oldPassword,
        newPassword: newPassword
      };
  
      this.http.post('http://localhost:8080/user/update-password', body, { 
        headers: headers, 
        withCredentials: true, 
        responseType: 'text' 
      })
      .subscribe({
        next: response => {
          switch (response) {
            case 'success':
              this.passwordUpdateMessage = 'Password updated successfully';
              break;
            case "failure. Old password doesn't match":
              this.passwordUpdateMessage = 'Old Password incorrect';
              break;
            default:
              this.passwordUpdateMessage = 'Unexpected error. Please try again later.';
              break;
          }
          console.log('Password update:', response);
        },
        error: error => {
          this.passwordUpdateMessage = 'An error occurred. Please try again later.';
          console.error('There was an error!', error);
        }
      });
    } else {
      console.error("Form is invalid!");
    }
  }
  
  
  
  loadPastOrders() {
    console.log("loading past orders");
    
    const csrfToken: string | null = this.getCSRFToken();
  
    if (csrfToken) {
      const headers = new HttpHeaders({
        'x-csrf-token': csrfToken
      });
  
      this.http.post('http://localhost:8080/user/orders', {}, { headers: headers, withCredentials: true }).subscribe((data: any) => {
        this.pastOrders = data;
      }, error => {
        console.log('Error loading past orders:', error);
      });
    }
  }
  
  
  



}

import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UserService } from '../service/user-service.service';
import { Router } from '@angular/router'; 

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  registerForm!: FormGroup;
  submitted = false;
  errorMessage: string | null = null; // Declare a local variable to handle errors
  registrationSuccess = false;

  constructor(private formBuilder: FormBuilder, private userService: UserService, private router: Router) { }

  ngOnInit(): void {
    this.registerForm = this.formBuilder.group({
      username: ['', Validators.required],
      first_name: ['', Validators.required],
      last_name: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      confirmPassword: ['', Validators.required]
    }, {
      validator: this.MustMatch('password', 'confirmPassword')
    });
  }

  onSubmit(): void {
    this.submitted = true;

    // stop here if form is invalid
    if (this.registerForm.invalid) {
      return;
    }

    this.userService.createUserForRegistration(this.registerForm.value).subscribe(
      data => {
        console.log(data);
        // Send a success message to main-content
        // const successMessage = `${this.registerForm.value.username} has been registered successfully`;
        // this.userService.changeMessage(successMessage);
  
        // Navigate to home page
        // this.router.navigate(['/']);
        console.log(data);
        this.registrationSuccess = true; // Add this line
      },
      error => {
        console.log(error.error); // Will print: "Sorry. Username already been taken"
        this.errorMessage = error.error; // Stor
      }
    );
  }

  onReset(): void {
    this.submitted = false;
    this.registrationSuccess = false;
    this.registerForm.reset();
  }

    MustMatch(controlName: string, matchingControlName: string) {
    return (formGroup: FormGroup) => {
        const control = formGroup.controls[controlName];
        const matchingControl = formGroup.controls[matchingControlName];

        if (matchingControl.errors && !matchingControl.errors['mustMatch']) {
            // return if another validator has already found an error on the matchingControl
            return;
        }

        // set error on matchingControl if validation fails
        if (control.value !== matchingControl.value) {
            matchingControl.setErrors({ mustMatch: 'Passwords do not match' });
        } else {
            matchingControl.setErrors(null);
        }
    }
}


}

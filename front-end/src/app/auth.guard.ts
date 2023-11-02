import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router } from '@angular/router';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(private router: Router) {}

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
      
    const username = localStorage.getItem('username');
  
    if (username) {
      return true;
    }
  
    // Save the attempted URL in localStorage
    localStorage.setItem('previous_url', state.url);
  
    // If username doesn't exist, redirect to login page
    this.router.navigate(['/login']).then(() => {
      // Clear the stored redirect URL after successful login
      localStorage.removeItem('redirectUrl');
    });
    return false;
  }
  
}

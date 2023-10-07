import { Component, OnInit, ElementRef, Renderer2, ViewChild, EventEmitter, Output, Input } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { NavigationEnd, Router } from '@angular/router';
import { Category } from '../models/category.model';
import { CartService } from '../service/cart.service';
import { ProductService } from '../service/product.service';
import { filter } from 'rxjs';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  @Output() userLoggedIn: EventEmitter<void> = new EventEmitter<void>();

  @Output() loginClicked: EventEmitter<void> = new EventEmitter<void>();
  @Output() loginCompleted: EventEmitter<void> = new EventEmitter<void>();

  onLoginCompleted(): void {
    this.loginCompleted.emit();
  }
  onLoginClicked(): void {
    this.loginClicked.emit();
  }

  @Input() refresh: boolean = false;


  @Output() hideProductList: EventEmitter<void> = new EventEmitter<void>();
  @Output() showProductList: EventEmitter<void> = new EventEmitter<void>();


  @ViewChild('dropdown') dropdown!: ElementRef;
  username: string | null = null;
  password: string | null = null;
  public categories: string[] = [];
  
  cartItemCount: number = 0;
  selectedCategory: string = 'All';


  constructor(private router: Router, private http: HttpClient, private renderer: Renderer2,private productService:ProductService,private cartService: CartService) { }

  ngOnInit(): void {
    this.username = localStorage.getItem('username');
    this.password = localStorage.getItem('password');
    this.getCategories();

    this.cartService.cartItemCount.subscribe((count: number) => {
      this.cartItemCount = count;
    });

    this.router.events.pipe(
      filter((event): event is NavigationEnd => event instanceof NavigationEnd)
    ).subscribe((event: NavigationEnd) => {
      // Refresh username
      this.username = localStorage.getItem('username');
      this.password = localStorage.getItem('password');
      console.log("Show product events");

      // Emit hideProductList
      if (event.url === '/' || event.url === '') {
        this.showProductList.emit();
        return;
        // Execute some code here if navigated to home page
      }
      this.hideProductList.emit();

      // // Emit hideProductList
      // if (this.username) {
      //   this.userLoggedIn.emit();
      //   // Execute some code here when username exists
      // }
        

    });

  }

  onCategorySelected(category: string): void {
    this.selectedCategory = category;
    this.productService.setSelectedCategory(category);
  }


  getCategories() {
    this.http.get<string[]>('http://localhost:8080/categories').subscribe((data: string[]) => {
      this.categories = data;
    });
}
isMenuDisplayed = false;

toggleMenu() {
  this.isMenuDisplayed = !this.isMenuDisplayed;
}

  logout(): void {
    const token = this.getCSRFToken();
    
    if (token) {
      const username = 'tayho';
      const password = 'kkkkkk';
      const basicAuth = 'Basic ' + btoa(username + ':' + password);
  
      const headers = new HttpHeaders({
        'x-csrf-token': token,
        'Authorization': basicAuth
      });
  
      this.http.post('http://localhost:8080/logout', {}, { headers: headers, withCredentials: true }).subscribe(() => {
        this.username = null;
        localStorage.removeItem('username');
        this.router.navigate(['/']);
      },
      (error) => {
        console.error(error);  // Log the error to console for debugging
        // Use whatever method you prefer for showing the error to the user.
        // This could be a simple alert, or a more user-friendly UI element in your application.
        alert('Logout failed. Error: ' + error.message);
      });
    }
  }
  
  

  updateCartItemCount(): void {
    this.cartItemCount = this.cartService.getCartSize();
  }

  onHideProductList(): void {
    this.hideProductList.emit();
  }
  onShowProductList(): void {
    this.hideProductList.emit();
  }

  returnToHomePage():void{
    console.log("returnToHome");
    this.productService.setSelectedCategory("All");
    this.router.navigate(['/']);

    this.showProductList.emit();
    
  }





  
  private createHeaders(): HttpHeaders {
    const csrfToken = this.getCSRFToken();
    const username = localStorage.getItem('username');
    const password = localStorage.getItem('password');

    let headers = new HttpHeaders();
    if(csrfToken) {
      headers = headers.append('x-csrf-token', csrfToken);
    }

 

    return headers;
  }

  private getCSRFToken(): string | null {
    return localStorage.getItem('csrfToken');
  }
}

import { Component, OnInit } from '@angular/core';
import { ProductService } from '../service/product.service';
import { Product } from '../models/product.model';
import { CartService } from '../service/cart.service';
import { Subscription, filter } from 'rxjs';
import { NavigationEnd, Router } from '@angular/router';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css']
})
export class ProductListComponent implements OnInit {

  apiURL = "http://localhost:8080/"
  currentPage = 1; // <-- Add this property here
  itemsPerPage = 10; // If you're also using itemsPerPage 
  products: Product[] = [];
  productQuantities: { [key: number]: number } = {}; // Object to store product quantities
  categorySubscription!: Subscription;


  constructor(
    private productService: ProductService,
    private cartService: CartService,
    private router: Router
  ) { }


  ngOnInit(): void {

    // Initial load of products
    this.productService.getProducts(0).subscribe(data => {
      this.products = data;
    });

    // Subscribe to categorySelected
    this.categorySubscription = this.productService.categorySelected$.subscribe((category: string) => {
      if (category === 'All') {
        this.productService.getProducts(0).subscribe(data => {
          this.products = data;
        });
      } else {
        this.productService.getProductsByCategory(category,0).subscribe(data => {
          this.products = data;
        });
      }
    });


    this.router.events.pipe(
      filter((event): event is NavigationEnd => event instanceof NavigationEnd)
    ).subscribe((event: NavigationEnd) => {
      console.log("Product List Page");
      // Refresh username
      if (event.url === '/' || event.url === '') {
        this.productService.getProducts(0).subscribe(data => {
          this.products = data;
        });
        // Execute some code here if navigated to home page
      }
    

      // // Emit hideProductList
      // if (this.username) {
      //   this.userLoggedIn.emit();
      //   // Execute some code here when username exists
      // }
        

    });
    
  }

  addToCart(product: Product): void {
    this.cartService.addProduct(product);
  }

  

  onPageChange(page: number) {
    this.currentPage = page;
    
    // Check for selected category and load products accordingly
    let  currentCategory = this.productService.getCurrentCategory();
    
    if (currentCategory === 'All') {
      this.productService.getProducts(this.currentPage - 1).subscribe(data => { // Page number starts from 0 in the backend
        this.products = data;
        // You might also want to update totalItems here based on the response, for the paginator to work correctly.
      });
    } else {


      this.productService.getProductsByCategory(currentCategory, this.currentPage - 1).subscribe(data => {
        this.products = data;
        // You might also want to update totalItems here based on the response, for the paginator to work correctly.
      });
    }
  }
  
  
}

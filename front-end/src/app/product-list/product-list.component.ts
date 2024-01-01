import { Component, OnInit } from '@angular/core';
import { ProductService } from '../service/product.service';
import { Product } from '../models/product.model';
import { CartService } from '../service/cart.service';
import { Subscription, filter } from 'rxjs';
import { NavigationEnd, Router } from '@angular/router';
import { DataFetchStrategy } from '../interface/DataFetchStrategy';
import { FetchProductsBySearchStrategy } from '../interface/FetchProductsBySearchStrategy';
import { FetchAllProductsStrategy } from '../interface/FetchAllProductsStrategy';
import { FetchProductsByCategoryStrategy } from '../interface/FetchProductsByCategoryStrategy ';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css']
})
export class ProductListComponent implements OnInit {

  title = 'WebSocketClient';
  stock?: string;
 
  private webSocket: WebSocket;


  

  apiURL = "http://localhost:8080/"
  currentPage = 1; // <-- Add this property here
  itemsPerPage = 10; // If you're also using itemsPerPage 
  products: Product[] = [];
  productQuantities: { [key: number]: number } = {}; // Object to store product quantities
  categorySubscription!: Subscription;
  currentCategory: string = '';
  searchSubscription!: Subscription;
  private dataFetchStrategy!: DataFetchStrategy;


  constructor(
    public productService: ProductService,
    private cartService: CartService,
    private router: Router,
    
  ) { 
    this.webSocket = new WebSocket('ws://localhost:8080/stocks');
    this.webSocket.onmessage = (event) => {
      this.stock = event.data
      console.log(event.data);
    };


  }

  
  ngOnInit(): void {

    // Initial load of products
    this.productService.getProducts(0).subscribe(data => {
      this.products = data;
    });

    // Subscribe to categorySelected
    this.categorySubscription = this.productService.categorySelected$.subscribe(category => {
      if (category === 'All') {
        this.dataFetchStrategy = new FetchAllProductsStrategy(this.productService);
      } else {
        // Strategy for fetching products by category
        // Assuming there is a strategy similar to FetchProductsBySearchStrategy
        // Replace with your actual implementation
        this.dataFetchStrategy = new FetchProductsByCategoryStrategy(this.productService, category);
      }
      this.onPageChange(0);
    });



    this.router.events.pipe(
      filter((event): event is NavigationEnd => event instanceof NavigationEnd)
    ).subscribe((event: NavigationEnd) => {
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

    this.searchSubscription = this.productService.searchQuery$.subscribe(query => {
      if (query) {
        this.dataFetchStrategy = new FetchProductsBySearchStrategy(this.productService, query);
        this.onPageChange(1);
      }
      else{
        this.productService.clearProductList();
        this.dataFetchStrategy = new FetchAllProductsStrategy(this.productService);
        this.onPageChange(0);

      }

    });

    // Default strategy
    this.dataFetchStrategy = new FetchAllProductsStrategy(this.productService);
    
  }

  sortProducts(order: string): void {
    if (order === 'ASC') {
      this.products.sort((a, b) => a.price - b.price);
    } else if (order === 'DSC') {
      this.products.sort((a, b) => b.price - a.price);
    }
  }
  addToCart(product: Product): void {
    this.cartService.addProduct(product);
  }

  

  // onPageChange(page: number) {
  //   this.currentPage = page;
    
  //   // Check for selected category and load products accordingly
  //   let  currentCategory = this.productService.getCurrentCategory();
    
  //   if (currentCategory === 'All') {
  //     this.productService.getProducts(this.currentPage - 1).subscribe(data => { // Page number starts from 0 in the backend
  //       this.products = data;
  //       // You might also want to update totalItems here based on the response, for the paginator to work correctly.
  //     });
  //   } else {


  //     this.productService.getProductsByCategory(currentCategory, this.currentPage - 1).subscribe(data => {
  //       this.products = data;
  //       // You might also want to update totalItems here based on the response, for the paginator to work correctly.
  //     });
  //   }
  // }
  onPageChange(page: number) {
    this.dataFetchStrategy.fetchData(page).subscribe(products => {
      this.products = products;
    });
  }
  sendMessage(message: string): void {
    this.webSocket.send(message);
  }
  
}
  


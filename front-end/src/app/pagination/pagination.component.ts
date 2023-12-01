import { Component, OnInit, OnDestroy, EventEmitter, Output } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ProductService } from '../service/product.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-pagination',
  templateUrl: './pagination.component.html',
  styleUrls: ['./pagination.component.css']
})
export class PaginationComponent implements OnInit, OnDestroy {
  apiUrl_all: string = "http://localhost:8080/products/count";
  apiUrl_category: string = "http://localhost:8080/products/{categoryType}/count";
  itemsPerPage: number = 10;
  currentPage: number = 1;
  @Output() pageChanged = new EventEmitter<number>();
  private categorySubscription!: Subscription;
  private searchSubscription!: Subscription;

  totalItems: number = 0;
  
  constructor(private http: HttpClient, private productService: ProductService) {}

  ngOnInit() {
    this.categorySubscription = this.productService.categorySelected$.subscribe(category => {
      this.currentPage = 1;
      this.fetchTotalItems(category);
    });

    this.searchSubscription = this.productService.getProductListObservable().subscribe(products => {
      // Update totalItems based on the current product list length
      this.totalItems = products.length;

      this.currentPage = 1;
    });
  }

  ngOnDestroy() {
    // Unsubscribe to avoid memory leaks
    this.categorySubscription.unsubscribe();
    this.searchSubscription.unsubscribe();
  }

  fetchTotalItems(category: string) {
    let apiUrl = category && category !== "All" 
                 ? this.apiUrl_category.replace('{categoryType}', category) 
                 : this.apiUrl_all;

    this.http.get<any>(apiUrl).subscribe(
      response => {
        this.totalItems = response.totalCount;
      },
      error => {
        console.error("Error fetching total items:", error);
      }
    );
  }

  get totalPages(): number {


    return Math.ceil(this.totalItems / this.itemsPerPage);
  }

  nextPage() {
    if (this.currentPage < this.totalPages) {
      this.currentPage++;
      this.pageChanged.emit(this.currentPage);
    }
  }
  
  prevPage() {
    console.log(`Current Page: ${this.currentPage}, Total Pages: ${this.totalPages}`);
    if (this.currentPage > 1) {
      this.currentPage--;
      this.pageChanged.emit(this.currentPage);
    }
  }
  
}

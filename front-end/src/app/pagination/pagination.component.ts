import { Component, Input, Output, EventEmitter, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ProductService } from '../service/product.service';
import { Subscription } from 'rxjs/internal/Subscription';

@Component({
  selector: 'app-pagination',
  templateUrl: './pagination.component.html',
  styleUrls: ['./pagination.component.css']
})
export class PaginationComponent implements OnInit {
  apiUrl_all: string = "http://localhost:8080/products/count";
  apiUrl_category: string = "http://localhost:8080/products/{categoryType}/count";
  itemsPerPage: number = 10;
  currentPage: number = 1;
  @Output() pageChanged = new EventEmitter<number>();
  private categorySubscription!: Subscription;

  totalItems: number = 0;

  constructor(private http: HttpClient, private productService: ProductService) {}

  ngOnInit() {
    this.categorySubscription = this.productService.categorySelected$.subscribe(category => {
      this.currentPage = 1;
      this.fetchTotalItems();
    });
  }

  fetchTotalItems() {
    let currentCategory = this.productService.getCurrentCategory();
    let apiUrl = (currentCategory && currentCategory !== "All") ? 
                 this.apiUrl_category.replace('{categoryType}', currentCategory) : 
                 this.apiUrl_all;
    this.http.get<any>(apiUrl).subscribe(
      response => {
        this.totalItems = response.totalCount;
      },
      error => {
        alert("Error: " + error.message);
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
    if (this.currentPage > 1) {
      this.currentPage--;
      this.pageChanged.emit(this.currentPage);
    }
  }
}

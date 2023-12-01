import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, BehaviorSubject, map, tap } from 'rxjs';
import { Product } from '../models/product.model';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private BASE_URL = 'http://localhost:8080';  // Replace with your Spring Boot backend API base URL
  private productList = new BehaviorSubject<Product[]>([]);

  private categorySelectedSubject: BehaviorSubject<string> = new BehaviorSubject<string>('All');
  public categorySelected$: Observable<string> = this.categorySelectedSubject.asObservable();

  private searchQuerySubject: BehaviorSubject<string> = new BehaviorSubject<string>('');
  public searchQuery$: Observable<string | null> = this.searchQuerySubject.asObservable();
  constructor(private http: HttpClient) { }

  // getProducts now returns a Page<ProductDTO>
  getProducts(page: number = 0): Observable<any> { 
    return this.http.get<any>(`${this.BASE_URL}/products?page=${page}`);
  }



  getProductsByCategory(category: string, page: number = 0): Observable<any> {
    return this.http.get<any>(`${this.BASE_URL}/products?category=${category}&page=${page}`);
  }

  setSelectedCategory(category: string): void {
    this.clearProductList();
    this.categorySelectedSubject.next(category);
  
  
  }
  
  
  public getCurrentCategory(): string {
    return this.categorySelectedSubject.getValue();
  }

  setSearchQuery(query: string): void {
    this.searchQuerySubject.next(query);
  }

  // Method to get products based on the search query
  getProductsBySearch(query: string): Observable<Product[]> {
    return this.http.get<Product[]>(`${this.BASE_URL}/products/search`, {
      params: { query: query }
    });
  }
  public getCurrentSearchQuery(): string{
     return this.searchQuerySubject.getValue();

  }
  setProductList(products: Product[]) {
    this.productList.next(products);
  }

  getProductsForPage(page: number, itemsPerPage: number = 10): Observable<Product[]> {
    const startIndex = (page - 1) * itemsPerPage;
    const endIndex = startIndex + itemsPerPage;
    return this.productList.asObservable().pipe(
      map(products => products.slice(startIndex, endIndex))
    );
  }
  getProductListObservable(): Observable<Product[]> {
    return this.productList.asObservable();
  }
  clearProductList() {
    this.productList.next([]);
  }

  sortProducts(order: string): void {
    const currentProducts = this.productList.getValue();

    let sortedProducts;
    if (order === 'ASC') {
      sortedProducts = [...currentProducts].sort((a, b) => a.price - b.price);
    } else if (order === 'DSC') {
      sortedProducts = [...currentProducts].sort((a, b) => b.price - a.price);
    } else {
      // Handle invalid sort order or default behavior
      sortedProducts = [...currentProducts];
    }

    this.productList.next(sortedProducts);
  }


  getProductList(): Product[] {
    return this.productList.getValue();
  }
}


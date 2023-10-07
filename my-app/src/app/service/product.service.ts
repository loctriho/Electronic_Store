import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, BehaviorSubject } from 'rxjs';
import { Product } from '../models/product.model';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private BASE_URL = 'http://localhost:8080';  // Replace with your Spring Boot backend API base URL

  private categorySelectedSubject: BehaviorSubject<string> = new BehaviorSubject<string>('All');
  public categorySelected$: Observable<string> = this.categorySelectedSubject.asObservable();

  constructor(private http: HttpClient) { }

  // getProducts now returns a Page<ProductDTO>
  getProducts(page: number = 0): Observable<any> { 
    return this.http.get<any>(`${this.BASE_URL}/products?page=${page}`);
  }

  getProductsByCategory(category: string, page: number = 0): Observable<any> {
    return this.http.get<any>(`${this.BASE_URL}/products?category=${category}&page=${page}`);
  }

  setSelectedCategory(category: string): void {
    this.categorySelectedSubject.next(category);
  
  
  }


  public getCurrentCategory(): string {
    return this.categorySelectedSubject.getValue();
  }
}

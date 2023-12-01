// fetch-products-by-category-strategy.ts

import { Observable } from 'rxjs';
import { ProductService } from '../service/product.service';
import { Product } from '../models/product.model';
import { DataFetchStrategy } from './DataFetchStrategy';

export class FetchProductsByCategoryStrategy implements DataFetchStrategy {
  constructor(private productService: ProductService, private category: string) {}

  fetchData(page: number): Observable<Product[]> {
    return this.productService.getProductsByCategory(this.category, page);
  }
}

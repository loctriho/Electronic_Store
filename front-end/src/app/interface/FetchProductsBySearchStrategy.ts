// fetch-products-by-search-strategy.ts

import { Observable, map, tap } from 'rxjs';
import { Product } from '../models/product.model';
import { ProductService } from '../service/product.service';
import { DataFetchStrategy } from './DataFetchStrategy';



export class FetchProductsBySearchStrategy implements DataFetchStrategy {
    constructor(private productService: ProductService, private query: string) {
      this.initializeProductList();
    }
  
    private initializeProductList() {
      this.productService.getProductsBySearch(this.query).pipe(
        tap(products => {
          this.productService.setProductList(products);
        })
      ).subscribe();
    }
  
    fetchData(page: number): Observable<Product[]> {
      // Assuming you want to fetch a paginated subset of the already loaded products
      return this.productService.getProductsForPage(page);
    }
  }

// interfaces/data-fetch-strategy.ts

import { Observable } from 'rxjs';
import { Product } from '../models/product.model'; // Update this import path based on your project structure

export interface DataFetchStrategy {
  fetchData(page: number): Observable<Product[]>;
}

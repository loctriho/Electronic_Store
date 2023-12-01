import { Observable } from "rxjs";
import { ProductService } from "../service/product.service";
import { Product } from "../models/product.model";
import { DataFetchStrategy } from "./DataFetchStrategy";


export class FetchAllProductsStrategy implements DataFetchStrategy {
    constructor(private productService: ProductService) {}
  
    fetchData(page: number): Observable<Product[]> {
      return this.productService.getProducts(page);
    }
  }
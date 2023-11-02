import { Product } from './product.model';

export interface Manufacturer {
    manufacturerId: number;
    manufacturerName: string;
    products: Product[];
}

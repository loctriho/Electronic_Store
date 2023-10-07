import { Category } from "./category.model";
import { Manufacturer } from "./manufacturer.model";

export interface Product {
    productId: number;
    productName: string;
    description: string;
    price: number;
    image: string;
    category: string;    // Category model should be defined
    manufacturer: string;   // Manufacturer model should be defined
}

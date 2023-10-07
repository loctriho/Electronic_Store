import { Injectable } from '@angular/core';
import { Product } from '../models/product.model';
import { Subject } from 'rxjs';
import { RoundDownPipe }   from '../pipe/round-down.pipe';

@Injectable({
  providedIn: 'root'
})
export class CartService {
  private cart: Product[] = [];
  private cartQuantities: { [key: number]: number } = {}; // Object to store cart product quantities
  private roundDownPipe = new RoundDownPipe();

  cartItemCount = new Subject<number>(); // Declare a new Subject for the cart item count

  constructor() {}

  addProduct(product: Product): void {
    // Check if the productId already exists in the cartQuantities object
    if (!this.cartQuantities.hasOwnProperty(product.productId)) {
        const quantity = this.cartQuantities[product.productId] || 0;
        this.cartQuantities[product.productId] = quantity + 1;
        this.cart.push(product);
        this.cartItemCount.next(this.getCartSize()); // Emit the new cart size
    }
}

removeProduct(product: Product): void {
    const quantity = this.cartQuantities[product.productId] || 0;
    if (quantity > 0) {
        this.cartQuantities[product.productId] = quantity - 1;
        const index = this.cart.findIndex(p => p.productId === product.productId);
        if (index > -1) {
            this.cart.splice(index, 1);
        }
        this.cartItemCount.next(this.getCartSize()); // Emit the new cart size
    }
    // Check if the quantity is 0, then delete the productId from cartQuantities
    if (this.cartQuantities[product.productId] === 0) {
        delete this.cartQuantities[product.productId];
    }
}


  getCart(): Product[] {
    return [...this.cart];
  }

  getCartSize(): number {
    return this.cart.length;
  }

  getTotalPrice(): number {
    let total = this.cart.reduce((total, product) => total + (product.price * this.getQuantity(product)), 0);
    return this.roundDownPipe.transform(total);
  }
  

  clearCart(): void {
    this.cart = [];
    this.cartQuantities = {};
    this.cartItemCount.next(0);
  }

  updateCartItemCount(): void {
    // Implement your logic to update the cart item count
  }

  getQuantity(product: Product): number {
    return this.cartQuantities[product.productId] || 0;
  }

  setQuantity(product: Product, quantity: number): void {
    if (quantity >= 0) {
      this.cartQuantities[product.productId] = quantity;
    }
  }

  getCartQuantities(){
    return this.cartQuantities;
    
  }
}

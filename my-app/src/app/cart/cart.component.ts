import { Component, OnInit } from '@angular/core';
import { Product } from '../models/product.model';
import { CartService } from '../service/cart.service';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit {
  cart: Product[] = [];

  constructor(private cartService: CartService) { }

  ngOnInit(): void {
    this.cart = this.cartService.getCart();
  }

  onRemoveProduct(product: Product): void {
    this.cartService.removeProduct(product);
  }

  onIncreaseQuantity(product: Product): void {
    const currentQuantity = this.cartService.getQuantity(product);
    this.cartService.setQuantity(product, currentQuantity + 1);
  }

  onDecreaseQuantity(product: Product): void {
    const currentQuantity = this.cartService.getQuantity(product);
    if(currentQuantity == 1){
      this.cartService.removeProduct(product);


    }
    if (currentQuantity > 0) {
      this.cartService.setQuantity(product, currentQuantity - 1);
      
    }
 
  }
  getCartItems() {
    return this.cartService.getCart();
}

  getProductQuantity(product: Product): number {
    return this.cartService.getQuantity(product);
  }
  getTotalPrice(): number {
    return this.cartService.getTotalPrice();
    
  }
}

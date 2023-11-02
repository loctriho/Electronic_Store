import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Address } from '../models/address.model';
import { OrderService } from '../service/order.service';
import { Product } from '../models/product.model';
import { CartService } from '../service/cart.service';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css']
})
export class CheckoutComponent implements OnInit {
  orderMessage: string = '';
  orderPlaced = false;  // new property

  checkoutForm!: FormGroup;
  username: string | null = null;
  email: string | null = null;
  first_name: string | null = null;
  last_name: string | null = null;
  address: Address = {
    street: '',
    apartment: '',
    city: '',
    state: '',
    zipCode: ''
  };

  cart: Product[] = []; // cart details
  cartQuantities: { [key: number]: number } = {}; // cart quantities
  totalAmount: number = 0; // total cart amount

  constructor(private orderService: OrderService, private cartService: CartService) { }

  ngOnInit(): void {
    this.username = localStorage.getItem('username');
    this.first_name = localStorage.getItem('first_name');
    this.last_name = localStorage.getItem('last_name');
    this.address.city = localStorage.getItem('city') ?? '';
    this.address.street = localStorage.getItem('streetNo') ?? '';
    this.address.apartment = localStorage.getItem('apartment') ?? '';
    this.address.zipCode = localStorage.getItem('zipCode') ?? '';
    this.address.state = localStorage.getItem('state') ?? '';

    // get cart details
    this.cart = this.cartService.getCart();
    this.cartQuantities = this.cartService.getCartQuantities();
    this.totalAmount = this.cartService.getTotalPrice();

    this.checkoutForm = new FormGroup({
      username: new FormControl({ value: this.username, disabled: true }),
      lastName: new FormControl({ value: this.last_name, disabled: true }),
      firstName: new FormControl({ value: this.first_name, disabled: true }),
      addressStreetNo: new FormControl(this.address.street, Validators.required),
      addressApartment: new FormControl(this.address.apartment),
      addressCity: new FormControl(this.address.city, Validators.required),
      addressState: new FormControl(this.address.state, Validators.required),
      addressZip: new FormControl(this.address.zipCode, Validators.required),
      defaultAddress: new FormControl(false),
      cardHolderName: new FormControl('', Validators.required),
      cardType: new FormControl('', Validators.required),
      cardNumber: new FormControl('', Validators.required),
      cvv: new FormControl('', [Validators.required, Validators.minLength(3), Validators.maxLength(3)]),
    });
  }

  onSubmit(): void {
    if (this.checkoutForm.valid) {
      const orderData = this.checkoutForm.value;

      // Include the disabled fields manually
      const transformedOrderData = {
        username: this.username,
        firstName: this.first_name,
        lastName: this.last_name,
        address: {
          street: orderData.addressStreetNo,
          apartment: orderData.addressApartment,
          city: orderData.addressCity,
          state: orderData.addressState,
          zipCode: orderData.addressZip
        },
        cardholder: orderData.cardHolderName,
        cardType: orderData.cardType,
        cardNumber: orderData.cardNumber,
        cvv: orderData.cvv,
        cartDetails: this.cart.map(product => ({
          productId: product.productId,
          price: product.price,
          quantity: this.cartQuantities[product.productId]
        })), // include cart details and quantities
        totalAmount: this.totalAmount
      }; // include total amount

      // Check if the "Use as Default Address" checkbox is checked
      const isDefaultAddress = orderData.defaultAddress;

      // Call the updateAddress observable if the checkbox is checked
      if (isDefaultAddress) {
        const addressData = {
          username: this.username,
          addressStreetNo: orderData.addressStreetNo,
          addressApartment: orderData.addressApartment,
          addressCity: orderData.addressCity,
          addressState: orderData.addressState,
          addressZip: orderData.addressZip
        };

        this.orderService.updateAddress(addressData).subscribe(
          response => {
            console.log('Default address updated successfully.');
            this.createOrderAndClearCart(transformedOrderData);
          },
          error => console.error('Error during address update:', error)
        );
      } else {
        // If the checkbox is not checked, directly create the order and clear the cart
        this.createOrderAndClearCart(transformedOrderData);
      }
    }
  }

  // Helper method to create order and clear cart
  private createOrderAndClearCart(orderData: any): void {
    this.orderService.createOrder(orderData).subscribe(
      response => {
        this.cartService.clearCart();
        this.orderPlaced = true; // set the flag to true
        this.orderMessage = `Thank you for your purchase. Your order id is ${response.orderId}`;
      },
      error => console.error('There was an error during the order creation', error)
    );
  }
  
}

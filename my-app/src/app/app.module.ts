import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';  // Import this
import { FormsModule, ReactiveFormsModule } from '@angular/forms'; // Import this if you haven't already
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { MainContentComponent } from './main-content/main-content.component';
import { LoginComponent } from './login/login.component';
import { RouterModule, Routes } from '@angular/router';
import { RegisterComponent } from './register/register.component';
import { ProductListComponent } from './product-list/product-list.component';
import { CartComponent } from './cart/cart.component';
import { CheckoutComponent } from './checkout/checkout.component';
import { AuthGuard } from './auth.guard';
import { AccountComponent } from './account/account.component';
import { RoundDownPipe }   from './pipe/round-down.pipe';
import { PaginationComponent } from './pagination/pagination.component';
// adjust the path based on your file structure

const routes: Routes = [
  { path: 'checkout', component: CheckoutComponent, canActivate: [AuthGuard] },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'cart', component: CartComponent },
  { path: 'account', component: AccountComponent }, // Define the route here

  // more routes here
];

@NgModule({
  declarations: [
    RoundDownPipe,
    AppComponent,
    HeaderComponent,
    MainContentComponent,
    LoginComponent,
    RegisterComponent,
    ProductListComponent,
    ProductListComponent,
    CartComponent,
    CheckoutComponent,
    AccountComponent,
    PaginationComponent,
    

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule, 
    HttpClientModule,  // Add this

    [RouterModule.forRoot(routes)
  ],
  ],
  providers: [],
  bootstrap: [AppComponent],
  
  exports: [RouterModule]
})
export class AppModule { }

import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  refreshHeader2: boolean = false;

  
  showHeader: boolean = true;
  showProductList: boolean = true;


  ngOnInit(): void {
    // ...existing code...

  }


  refreshHeader(): void {
    this.showHeader = false;
    setTimeout(() => {
      this.showHeader = true;
    }, 0);
  }

  onHideProductList() {
    this.showProductList = false;
  }

  onShowProductList() {
    this.showProductList = true;
  }

  onLoginClicked(): void {
    this.showProductList = false;
  }
  checkUserLoggedIn(): void{
    this.showProductList = true;



  }
}

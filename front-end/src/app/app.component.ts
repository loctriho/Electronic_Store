import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  refreshHeader2: boolean = false;
  title = 'WebSocketClient';
  stock?: string;
 
  private webSocket: WebSocket;

  
  constructor() {
    this.webSocket = new WebSocket('ws://localhost:8080/stocks');
    this.webSocket.onmessage = (event) => {
      this.stock = event.data
      console.log(event.data);
    };
  } 
  showHeader: boolean = true;
  showProductList: boolean = true;


  ngOnInit(): void {
    // ...existing code...

  }
  sendMessage(message: string): void {
    this.webSocket.send(message);
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

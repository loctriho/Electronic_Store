import { Component, OnInit } from '@angular/core';
import { UserService } from '../service/user-service.service';

@Component({
  selector: 'app-main-content',
  templateUrl: './main-content.component.html',
  styleUrls: ['./main-content.component.css']
})
export class MainContentComponent implements OnInit {
  message: any;

  constructor(private userService: UserService) { }

  ngOnInit(): void {
    this.userService.currentMessage.subscribe(message => this.message = message);
  }
}

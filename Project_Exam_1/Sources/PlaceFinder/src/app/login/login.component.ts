import { Component, OnInit } from '@angular/core';
import { DataService } from '../services/data.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  user: string;

  constructor(private dataService: DataService) { }

  ngOnInit(): void {
    document.body.classList.add('bg-img');
  }

  loginUser() {
    this.dataService.loginUser(this.user);
  }
}

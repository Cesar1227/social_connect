import { Component, OnInit } from '@angular/core';
import { Route, Router } from '@angular/router';
import { LoginService } from 'src/app/services/login.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(loginService:LoginService, router:Router) {
    if (loginService.getUser()!=null) {
      router.navigate(['/dashboard']);
    }else{
      router.navigate(['/login']);
    }
   }

  ngOnInit(): void {
  }

}
